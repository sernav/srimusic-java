package es.uclm.sri.sis.reflexion;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.management.RuntimeErrorException;

import es.uclm.sri.sis.excepciones.ClaseNoEncontrada;
import es.uclm.sri.sis.excepciones.ExcepcionGeneral;

/**
 * @author Sergio Navarro
 * 
 * Clase de utilidades para el uso de la reflexión metodal
 */
public final class Reflexion {

	public Reflexion() {
		super();
	}

	public static Object ejecutarMetodoEstatico(String sourceName, String metodo) {
		Class invokedClass = null;
		try {
			invokedClass = Class.forName(sourceName);
		} catch (ClassNotFoundException e) {
			throw getError(e, "Clase " + sourceName + " no encontrada");
		}
		Method method = null;
		try {
			method = invokedClass.getMethod(metodo, new Class[0]);
		} catch (SecurityException e1) {
			throw getError(e1, "Excepción de seguridad en  " + invokedClass);
		} catch (NoSuchMethodException e1) {
			throw getError(e1, "No existe método " + metodo + " en " + invokedClass);
		}
		try {
			return method.invoke(null, new Object[0]);
		} catch (IllegalArgumentException e2) {
			throw getError(e2, "Argumento ilegal al invocar método " + metodo + " en " + invokedClass);

		} catch (IllegalAccessException e2) {
			throw getError(e2, "Acceso ilegal al invocar método " + metodo + " en " + invokedClass);
		} catch (InvocationTargetException e2) {
			throw getError(e2, "Excepción en destino de invocación para  " + invokedClass);
		}
	}

	public static Object ejecutarMetodoEstatico(String sourceName, String metodo, String argumento) {
		Class invokedClass = null;
		try {
			invokedClass = Class.forName(sourceName);
		} catch (ClassNotFoundException e) {
			throw getError(e, "Clase " + sourceName + " no encontrada");
		}
		Method method = null;
		Class[] tipoArgs = { String.class };
		try {
			method = invokedClass.getMethod(metodo, tipoArgs);
		} catch (SecurityException e1) {
			throw getError(e1, "Excepción de seguridad en  " + invokedClass);
		} catch (NoSuchMethodException e1) {
			throw getError(e1, "No existe método " + metodo + " en " + invokedClass);
		}
		Object[] args = { argumento };
		try {
			return method.invoke(null, args);
		} catch (IllegalArgumentException e2) {
			throw getError(e2, "Argumento Ilegal al invocar método " + metodo + " en " + invokedClass);

		} catch (IllegalAccessException e2) {
			throw getError(e2, "Acceso Ilegal al invocar método " + metodo + " en " + invokedClass);
		} catch (InvocationTargetException e2) {
			ExcepcionGeneral eg = new ExcepcionGeneral(e2.getTargetException(), "Excepción en destino de invocación para  " + invokedClass);
		}
		return method;
	}

	public static Object ejecutarMetodoEstatico(String sourceName, String metodo, Object[] args) {
		Class invokedClass = null;
		try {
			invokedClass = Class.forName(sourceName);
		} catch (ClassNotFoundException e) {
			throw getError(e, "Clase " + sourceName + " no encontrada");
		}
		Method method = null;
		Class[] tipoArgs = new Class[args.length];
		for (int i = 0; i < tipoArgs.length; i++) {
			tipoArgs[i] = args[i].getClass();
		}

		try {
			method = invokedClass.getMethod(metodo, tipoArgs);
		} catch (SecurityException e1) {
			throw getError(e1, "Excepción de seguridad en  " + invokedClass);
		} catch (NoSuchMethodException e1) {
			throw getError(e1, "No existe método " + metodo + " en " + invokedClass);
		}
		try {
			return method.invoke(null, args);
		} catch (IllegalArgumentException e2) {
			throw getError(e2, "Argumento Ilegal al invocar método " + metodo + " en " + invokedClass);

		} catch (IllegalAccessException e2) {
			throw getError(e2, "Acceso Ilegal al invocar método " + metodo + " en " + invokedClass);
		} catch (InvocationTargetException e2) {
		    ExcepcionGeneral eg = new ExcepcionGeneral(e2.getTargetException(), "Excepción en destino de invocación para  " + invokedClass);
		}
		return method;
	}
	
	public static Object ejecutarMetodoDObject(Object object, String metodo) {
	    Object result = null;
	    if (object != null) {
	        try {
                Method method =  object.getClass().getMethod(metodo, new Class<?>[0]);
                result = method.invoke(object, new Object[0]);
            } catch (SecurityException e1) {
                throw getError(e1, "Excepción de seguridad en  " + object.getClass().getName());
            } catch (NoSuchMethodException e1) {
                throw getError(e1, "No existe método " + metodo + " en " + object.getClass().getName());
            } catch (Throwable e1) {
                getError((Exception) e1, "Excepción al invocar el método " + metodo + " del objeto de la clase " + object.getClass().getName());
            }
	    }
        return result;
	}

	public static Object newInstace(String nameClass, Class[] tiposArgumentos, Object[] argumentos) throws ExcepcionGeneral {
		Class clase = getClass(nameClass);
		return newInstace(clase, tiposArgumentos, argumentos);
	}

	public static Object newInstace(Class clase, Class[] tiposArgumentos, Object[] argumentos) throws ExcepcionGeneral {
		Constructor constructor = getConstructor(clase, tiposArgumentos);
		try {
			return constructor.newInstance(argumentos);
		} catch (IllegalArgumentException e) {
		    ExcepcionGeneral eg = new ExcepcionGeneral(new String("ExpGral_" + Reflexion.class.getName() + ".newInstance_001"), clase.getName());
			throw eg;
		} catch (InstantiationException e) {
		    ExcepcionGeneral eg = new ExcepcionGeneral(new String("ExpGral_" + Reflexion.class.getName() + ".newInstance_002"), clase.getName());
			throw eg;
		} catch (IllegalAccessException e) {
		    ExcepcionGeneral eg = new ExcepcionGeneral(new String("ExpGral_" + Reflexion.class.getName() + ".newInstance_003"), clase.getName());
			throw eg;
		} catch (InvocationTargetException e) {
			Throwable th = e.getTargetException();
			if (th instanceof ExcepcionGeneral) {
				throw (ExcepcionGeneral) th;
			}
			ExcepcionGeneral eg = new ExcepcionGeneral(new String("ExpGral_" + Reflexion.class.getName() + ".newInstance_004"), clase.getName() + th.getMessage());
			throw eg;
		}
	}

	public static Object newInstace(String nameClass) throws ExcepcionGeneral {
		Class[] tiposArgumentos = new Class[0];
		Object[] argumentos = new Object[0];
		Class clase = getClass(nameClass);
		return newInstace(clase, tiposArgumentos, argumentos);
	}

	public static Object newInstace(Class clase) throws ExcepcionGeneral {
		Class[] tiposArgumentos = new Class[0];
		Object[] argumentos = new Object[0];
		return newInstace(clase, tiposArgumentos, argumentos);
	}

	public static Class getClass(String nameSource) throws ExcepcionGeneral {
		Class clase;
		try {
			clase = Class.forName(nameSource);
			return clase;
		} catch (ClassNotFoundException e1) {
		    ClaseNoEncontrada eg = new ClaseNoEncontrada(new String("ClassNotFound_" + Reflexion.class.getName() + ".getClass_001"), nameSource);
			eg.setStackTrace(e1.getStackTrace());
			throw eg;
		} catch (Throwable e2) {
		    ExcepcionGeneral eg = new ExcepcionGeneral(new String("ExpGral_" + Reflexion.class.getName() + ".getClass_001"), nameSource);
			eg.setStackTrace(e2.getStackTrace());
			throw eg;
		}
	}

	public static boolean exists(String className) {
		try {
			getClass(className);
		} catch (Throwable e) {
			return false;
		}
		return true;
	}

	private static Constructor getConstructor(Class clase, Class[] tiposArgumentos) throws ExcepcionGeneral {
		try {
			Constructor constructor = clase.getConstructor(tiposArgumentos);
			return constructor;
		} catch (SecurityException e2) {
		    ExcepcionGeneral eg = new ExcepcionGeneral(new String("ExpGral_" + Reflexion.class.getName() + ".getConstructor_001"), clase.getName());
			eg.setStackTrace(e2.getStackTrace());
			throw eg;
		} catch (NoSuchMethodException e2) {
		    ExcepcionGeneral eg = new ExcepcionGeneral(new String("ExpGral_" + Reflexion.class.getName() + ".getConstructor_002"), clase.getName());
			eg.setStackTrace(e2.getStackTrace());
			throw eg;
		}
	}

	private static RuntimeErrorException getError(Exception e, String mensaje) {
	    Error error = new Error(mensaje);
		RuntimeErrorException rt = new RuntimeErrorException(error);
		rt.setStackTrace(e.getStackTrace());
		return rt;
	}

}