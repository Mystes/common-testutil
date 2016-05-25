<?xml version="1.0" encoding="ISO-8859-15"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output encoding="ISO-8859-15" indent="yes" method="xml" version="1.0" />
    <xsl:strip-space elements="*" />
    <xsl:param name="param1"/>
    <xsl:param name="param2"/>

    <xsl:template match="/">
        <transformedData>
            <transformedField1><xsl:value-of select="//field1"/></transformedField1>
            <transformedField2><xsl:value-of select="//field2"/></transformedField2>
            <parameterValue1><xsl:value-of select="$param1"/></parameterValue1>
            <parameterValue2><xsl:value-of select="$param2"/></parameterValue2>
        </transformedData>
    </xsl:template>

</xsl:stylesheet>