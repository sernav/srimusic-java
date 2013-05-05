<?xml version="2.0" encoding="utf-8"?>
<!--
Declare a namespace for the XHTML, otherwise the XPATH will not work.
-->
<xsl:stylesheet	
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:xhtml="http://www.w3.org/1999/xhtml"
	version="1.0">

<xsl:output method="text" encoding="UTF-8"/>
<xsl:strip-space elements="*"/>

<xsl:variable name="newline">
<xsl:text>
</xsl:text>
</xsl:variable>

<xsl:template match="/">
Blog title, URL
<xsl:for-each select="xhtml:html/xhtml:body/xhtml:div/xhtml:div[@id='innercontent']/xhtml:dl/xhtml:dd/xhtml:a"><!--
Extract real URL is stored. It's easy to pull with two
substrings, one to remove the beginning of the Javascript and the other to remove
the end.
--><xsl:value-of select="substring(substring(@onmouseover, 17,string-length(@onmouseover)), 1,string-length(substring(@onmouseover, 17,string-length(@onmouseover)))-14)"/><xsl:text>, </xsl:text><xsl:value-of select="text()"/><xsl:value-of select="$newline"/></xsl:for-each>
</xsl:template>

</xsl:stylesheet>
