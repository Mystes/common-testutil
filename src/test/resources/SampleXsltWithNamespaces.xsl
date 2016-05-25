<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output encoding="UTF-8" indent="yes" method="xml" version="1.0" />
    <xsl:strip-space elements="*" />
    <xsl:param name="param1"/>
    <xsl:param name="param2"/>

    <xsl:template match="/">
        <transformedData xmlns="http://sample.schema.mystes.fi">
            <transformedField1><xsl:value-of select="//field1"/></transformedField1>
            <transformedField2><xsl:value-of select="//field2"/></transformedField2>
        </transformedData>
    </xsl:template>

</xsl:stylesheet>