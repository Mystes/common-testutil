<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"  xmlns:xs="http://www.w3.org/2001/XMLSchema">
    <xsl:output encoding="UTF-8" indent="yes" method="xml" version="1.0" />
    <xsl:strip-space elements="*" />

    <xsl:template match="/">
        <transformedData>
            <transformedDate><xsl:value-of select="format-date(xs:date(//date), '[D].[M].[Y0001]')"/></transformedDate>
        </transformedData>
    </xsl:template>

</xsl:stylesheet>