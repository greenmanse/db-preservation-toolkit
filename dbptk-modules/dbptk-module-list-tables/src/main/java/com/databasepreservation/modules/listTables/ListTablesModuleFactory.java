package com.databasepreservation.modules.listTables;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import com.databasepreservation.model.Reporter;
import com.databasepreservation.model.exception.LicenseNotAcceptedException;
import com.databasepreservation.model.modules.DatabaseExportModule;
import com.databasepreservation.model.modules.DatabaseImportModule;
import com.databasepreservation.model.modules.DatabaseModuleFactory;
import com.databasepreservation.model.parameters.Parameter;
import com.databasepreservation.model.parameters.Parameters;

/**
 * Exposes an export module that produces a list of tables contained in the
 * database. This list can then be used by other modules (e.g. the SIARD2 export
 * module) to specify the tables that should be processed.
 *
 * @author Bruno Ferreira <bferreira@keep.pt>
 */
public class ListTablesModuleFactory implements DatabaseModuleFactory {
  private static final Parameter file = new Parameter().shortName("f").longName("file")
    .description("Path to output file that can be read by SIARD2 export module").hasArgument(true)
    .setOptionalArgument(false).required(true);

  @Override
  public boolean producesImportModules() {
    return false;
  }

  @Override
  public boolean producesExportModules() {
    return true;
  }

  @Override
  public String getModuleName() {
    return "list-tables";
  }

  @Override
  public Map<String, Parameter> getAllParameters() {
    HashMap<String, Parameter> parameterHashMap = new HashMap<String, Parameter>();
    parameterHashMap.put(file.longName(), file);
    return parameterHashMap;
  }

  @Override
  public Parameters getImportModuleParameters() throws OperationNotSupportedException {
    throw DatabaseModuleFactory.ExceptionBuilder.OperationNotSupportedExceptionForImportModule();
  }

  @Override
  public Parameters getExportModuleParameters() throws OperationNotSupportedException {
    return new Parameters(Arrays.asList(file), null);
  }

  @Override
  public DatabaseImportModule buildImportModule(Map<Parameter, String> parameters)
    throws OperationNotSupportedException, LicenseNotAcceptedException {
    throw DatabaseModuleFactory.ExceptionBuilder.OperationNotSupportedExceptionForImportModule();
  }

  @Override
  public DatabaseExportModule buildExportModule(Map<Parameter, String> parameters)
    throws OperationNotSupportedException, LicenseNotAcceptedException {
    Path pFile = Paths.get(parameters.get(file));

    Reporter.exportModuleParameters(this.getModuleName(), "file", pFile.normalize().toAbsolutePath().toString());
    return new ListTables(pFile);
  }
}
