package com.databasepreservation.modules.siard.out.path;

import com.databasepreservation.model.structure.SchemaStructure;
import com.databasepreservation.model.structure.TableStructure;

/**
 * Interface to describe paths to folders and files for some SIARD archive.
 * <p>
 * Paths to files should NOT end with a slash and are relative to the root of
 * the SIARD archive Namespaces should have a prefix and some parameters to
 * build a URL Names are names of folders or files (for files it should also
 * contain the extension)
 *
 * @author Bruno Ferreira <bferreira@keep.pt>
 */
public interface ContentPathExportStrategy {
  //-------------------------------------------------------------------------------------------------------------------
  default String getClobFilePath(SchemaStructure schemaStructure, TableStructure tableStructure, int columnIndex, int rowIndex) {
    return getClobFilePath(schemaStructure.getIndex(), tableStructure.getIndex(), columnIndex, rowIndex);
  }

  /**
   * Returns the path to a LOB file
   *
   * @param schemaIndex
   *          Schema index (begins at 1)
   * @param tableIndex
   *          Table index (begins at 1)
   * @param columnIndex
   *          Column index (begins at 1)
   * @param rowIndex
   *          Row index (begins at 0)
   */
  public String getClobFilePath(int schemaIndex, int tableIndex, int columnIndex, int rowIndex);

  //-------------------------------------------------------------------------------------------------------------------
  default String getBlobFilePath(SchemaStructure schemaStructure, TableStructure tableStructure, int columnIndex, int rowIndex) {
    return getBlobFilePath(schemaStructure.getIndex(), tableStructure.getIndex(), columnIndex, rowIndex);
  }

  /**
   * Returns the path to a LOB file
   *
   * @param schemaIndex
   *          Schema index (begins at 1)
   * @param tableIndex
   *          Table index (begins at 1)
   * @param columnIndex
   *          Column index (begins at 1)
   * @param rowIndex
   *          Row index (begins at 0)
   */
  public String getBlobFilePath(int schemaIndex, int tableIndex, int columnIndex, int rowIndex);

  /**
   * Returns the name of the database schema folder
   *
   * @param schema
   *          database schema
   */
  default public String getSchemaFolderName(SchemaStructure schema) {
    return getSchemaFolderName(schema.getIndex());
  };

  /**
   * Returns the name of the database schema folder
   *
   * @param schemaIndex
   *          database schema index (begins at 1)
   */
  public String getSchemaFolderName(int schemaIndex);

  /**
   * Returns the name of the table column folder
   *
   * @param columnIndex
   *          table column index (begins at 1)
   */
  public String getColumnFolderName(int columnIndex);

  /**
   * Returns the name of a table's folder
   *
   * */
  default public String getTableFolderName(TableStructure table) {
    return getTableFolderName(table.getIndex());
  }

  /**
   * Returns the name of a table's folder
   *
   * @param tableIndex
   */
  public String getTableFolderName(int tableIndex);

  /**
   * Returns the path to a table's XML file
   *
   */
  default public String getTableXsdFilePath(SchemaStructure schema, TableStructure table) {
    return getTableXsdFilePath(schema.getIndex(), table.getIndex());
  }

  /**
   * Returns the path to a table's XML file
   *
   * @param schemaIndex
   *          database schema index (begins at 1)
   * @param tableIndex
   *          table index (begins at 1)
   */
  public String getTableXsdFilePath(int schemaIndex, int tableIndex);

  /**
   * Returns the path to a table's XSD file
   *
   */
  default public String getTableXmlFilePath(SchemaStructure schema, TableStructure table) {
    return getTableXmlFilePath(schema.getIndex(), table.getIndex());
  }

  /**
   * Returns the path to a table's XSD file
   *
   * @param schemaIndex
   *          database schema index (begins at 1)
   * @param tableIndex
   *          table index (begins at 1)
   */
  public String getTableXmlFilePath(int schemaIndex, int tableIndex);

  //-------------------------------------------------------------------------------------------------------------------
  default String getTableXsdNamespace(String base, SchemaStructure schemaStructure, TableStructure tableStructure) {
    return getTableXsdNamespace(base, schemaStructure.getIndex(), tableStructure.getIndex());
  }

  /**
   * Returns the XML schema URL to use in XML namespace declaration
   *
   * @param schemaIndex
   *          database schema index (begins at 1)
   * @param tableIndex
   *          table index (begins at 1)
   */
  public String getTableXsdNamespace(String base, int schemaIndex, int tableIndex);

  //-------------------------------------------------------------------------------------------------------------------
  default String getTableXsdFileName(TableStructure tableStructure) {
    return getTableXsdFileName(tableStructure.getIndex());
  };

  /**
   * Returns the name of the XML schema file for the specified table
   *
   * @param tableIndex
   */
  public String getTableXsdFileName(int tableIndex);
}
