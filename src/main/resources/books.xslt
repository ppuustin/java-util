<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 <xsl:template match="/">
  <html>
  <body>
  <h2>Books</h2>
  <table border="1">
    <tr bgcolor="#C0C0C0">
      <th>id</th>
      <th>name</th>
      <th>author</th>
    </tr>
    <xsl:for-each select="books/book">
    <tr>
    <td><xsl:value-of select="@id"/></td>
      <td><xsl:value-of select="name"/></td>
      <td><xsl:value-of select="author"/></td>
    </tr>
    </xsl:for-each>
  </table>
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>