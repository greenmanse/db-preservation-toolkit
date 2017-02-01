package com.databasepreservation.modules.siard.out.path;

import com.databasepreservation.model.structure.SchemaStructure;
import com.databasepreservation.model.structure.TableStructure;

/**
 * @author Bruno Ferreira <bferreira@keep.pt>
 */
public class SIARD2DescriptiveContentPathExportStrategy extends SIARD2ContentPathExportStrategy {
  // names for directories
  protected static final String CONTENT_DIR = "content";
  protected static final String LOB_DIR = "lob";

  // names for files
  protected static final String LOB_FILENAME = "record";

  // extensions for files
  protected static final String XML_EXTENSION = "xml";
  protected static final String XSD_EXTENSION = "xsd";
  public static final String CLOB_EXTENSION = "txt";
  public static final String BLOB_EXTENSION = "bin";

  // control characters
  protected static final String RESOURCE_FILE_SEPARATOR = "/";
  protected static final String FILE_EXTENSION_SEPARATOR = ".";

  public String getClobFileName(int rowIndex) {
    return new StringBuilder().append(LOB_FILENAME).append(rowIndex).append(FILE_EXTENSION_SEPARATOR)
      .append(CLOB_EXTENSION).toString();
  }

  public String getBlobFileName(int rowIndex) {
    return new StringBuilder().append(LOB_FILENAME).append(rowIndex).append(FILE_EXTENSION_SEPARATOR)
      .append(BLOB_EXTENSION).toString();
  }

  //-------------------------------------------------------------------------------------------------------------------
  @Override
  public String getClobFilePath(SchemaStructure schemaStructure, TableStructure tableStructure, int columnIndex, int rowIndex) {
    return getTableDirPathBuilder(schemaStructure, tableStructure)
      .append(RESOURCE_FILE_SEPARATOR).append(LOB_DIR).append(columnIndex).append(RESOURCE_FILE_SEPARATOR)
      .append(LOB_FILENAME).append(rowIndex).append(FILE_EXTENSION_SEPARATOR).append(CLOB_EXTENSION).toString();
  }

  //-------------------------------------------------------------------------------------------------------------------
  @Override
  public String getBlobFilePath(SchemaStructure schemaStructure, TableStructure tableStructure, int columnIndex, int rowIndex) {
    return getTableDirPathBuilder(schemaStructure, tableStructure)
      .append(RESOURCE_FILE_SEPARATOR).append(LOB_DIR).append(columnIndex).append(RESOURCE_FILE_SEPARATOR)
      .append(LOB_FILENAME).append(rowIndex).append(FILE_EXTENSION_SEPARATOR).append(BLOB_EXTENSION).toString();
  }

  //-------------------------------------------------------------------------------------------------------------------
  @Override
  public String getSchemaFolderName(SchemaStructure schemaStructure) {
      return schemaStructure.getName();
  }

  //-------------------------------------------------------------------------------------------------------------------
  @Override
  public String getTableFolderName(TableStructure tableStructure) {
      return tableStructure.getName();
  }

  //-------------------------------------------------------------------------------------------------------------------
  @Override
  public String getTableXsdFilePath(SchemaStructure schema, TableStructure table) {
    return getTableDirPathBuilder(schema, table)
        .append(RESOURCE_FILE_SEPARATOR).append(table.getName()).append(FILE_EXTENSION_SEPARATOR)
        .append(XSD_EXTENSION).toString();
  }

  //-------------------------------------------------------------------------------------------------------------------
  @Override
  public String getTableXmlFilePath(SchemaStructure schemaStructure, TableStructure tableStructure) {
    return getTableDirPathBuilder(schemaStructure, tableStructure)
      .append(RESOURCE_FILE_SEPARATOR).append(tableStructure.getName()).append(FILE_EXTENSION_SEPARATOR)
      .append(XML_EXTENSION).toString();
  }

  //-------------------------------------------------------------------------------------------------------------------
  @Override
  public String getTableXsdNamespace(String base, SchemaStructure schemaStructure, TableStructure tableStructure) {
    return new StringBuilder().append(base).append(schemaStructure.getName()).append(RESOURCE_FILE_SEPARATOR)
      .append(tableStructure.getName()).append(FILE_EXTENSION_SEPARATOR).append(XSD_EXTENSION).toString();
  }

  //-------------------------------------------------------------------------------------------------------------------
  @Override
  public String getTableXsdFileName(TableStructure tableStructure) {
    return new StringBuilder().append(tableStructure.getName()).append(FILE_EXTENSION_SEPARATOR)
      .append(XSD_EXTENSION).toString();
  }

  //-------------------------------------------------------------------------------------------------------------------
  private StringBuilder getSchemaDirPathBuilder(SchemaStructure schema) {
    StringBuilder stringBuilder = new StringBuilder().append(CONTENT_DIR).append(RESOURCE_FILE_SEPARATOR);

      return stringBuilder.append(schema.getName());
  }

  //-------------------------------------------------------------------------------------------------------------------
  private StringBuilder getTableDirPathBuilder(SchemaStructure schema, TableStructure table) {
    StringBuilder withSchemaBuilder = getSchemaDirPathBuilder(schema).append(RESOURCE_FILE_SEPARATOR);

      String tableFolderName = getTableFolderName(table);
      return withSchemaBuilder.append(tableFolderName);
  }
}
