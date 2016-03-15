package com.databasepreservation.modules.siard;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.apache.commons.lang3.StringUtils;

import com.databasepreservation.model.modules.DatabaseExportModule;
import com.databasepreservation.model.modules.DatabaseImportModule;
import com.databasepreservation.model.modules.DatabaseModuleFactory;
import com.databasepreservation.model.parameters.Parameter;
import com.databasepreservation.model.parameters.Parameters;
import com.databasepreservation.modules.siard.in.input.SIARD2ImportModule;
import com.databasepreservation.modules.siard.out.output.SIARD2ExportModule;

/**
 * @author Bruno Ferreira <bferreira@keep.pt>
 */
public class SIARD2ModuleFactory implements DatabaseModuleFactory {
  private static final Parameter file = new Parameter().shortName("f").longName("file")
    .description("Path to SIARD2 archive file").hasArgument(true).setOptionalArgument(false).required(true);

  // TODO: check if this argument is really necessary
  // private static final Parameter auxiliaryContainersInZipFormat = new
  // Parameter().shortName("p")
  // .longName("pretty-xml").description(
  // "In some SIARD2 archives, LOBs are saved outside the main SIARD archive container. These LOBs "
  // +
  // "may be saved in a ZIP or simply saved to folders. When reading those LOBs it's important "
  // +
  // "to know if they are inside a simple folder or a zip container.").hasArgument(false).required(false)
  // .valueIfNotSet("false").valueIfSet("true");

  private static final Parameter compress = new Parameter().shortName("c").longName("compress")
    .description("use to compress the SIARD2 archive file with deflate method").hasArgument(false).required(false)
    .valueIfNotSet("false").valueIfSet("true");

  private static final Parameter prettyPrintXML = new Parameter().shortName("p").longName("pretty-xml")
    .description("write human-readable XML").hasArgument(false).required(false).valueIfNotSet("false")
    .valueIfSet("true");

  private static final Parameter tableFilter = new Parameter()
    .shortName("tf")
    .longName("table-filter")
    .description(
      "file with the list of tables that should be exported (this file can be created by the list-tables export module).")
    .required(false).hasArgument(true).setOptionalArgument(false);

  private static final Parameter externalLobs = new Parameter().shortName("el").longName("external-lobs")
    .description("Saves any LOBs outside the siard file.").required(false).hasArgument(false).valueIfSet("true")
    .valueIfNotSet("false");

  private static final Parameter externalLobsPerFolder = new Parameter().shortName("elpf")
    .longName("external-lobs-per-folder")
    .description("The maximum number of files present in an external LOB folder. Default: 1000 files.").required(false)
    .hasArgument(true).setOptionalArgument(false).valueIfNotSet("1000");

  private static final Parameter externalLobsFolderSize = new Parameter()
    .shortName("elfs")
    .longName("external-lobs-folder-size")
    .description(
      "Divide LOBs across multiple external folders with (approximately) the specified maximum size (in Megabytes). Default: do not divide.")
    .required(false).hasArgument(true).setOptionalArgument(false).valueIfNotSet("0");

  @Override
  public boolean producesImportModules() {
    return true;
  }

  @Override
  public boolean producesExportModules() {
    return true;
  }

  @Override
  public String getModuleName() {
    return "siard-2";
  }

  @Override
  public Map<String, Parameter> getAllParameters() {
    HashMap<String, Parameter> parameterHashMap = new HashMap<String, Parameter>();
    parameterHashMap.put(file.longName(), file);
    parameterHashMap.put(compress.longName(), compress);
    parameterHashMap.put(prettyPrintXML.longName(), prettyPrintXML);
    parameterHashMap.put(tableFilter.longName(), tableFilter);
    parameterHashMap.put(externalLobs.longName(), externalLobs);
    parameterHashMap.put(externalLobsPerFolder.longName(), externalLobsPerFolder);
    parameterHashMap.put(externalLobsFolderSize.longName(), externalLobsFolderSize);
    return parameterHashMap;
  }

  @Override
  public Parameters getImportModuleParameters() throws OperationNotSupportedException {
    return new Parameters(Arrays.asList(file), null);
  }

  @Override
  public Parameters getExportModuleParameters() throws OperationNotSupportedException {
    return new Parameters(Arrays.asList(file, compress, prettyPrintXML, tableFilter, externalLobs,
      externalLobsPerFolder, externalLobsFolderSize), null);
  }

  @Override
  public DatabaseImportModule buildImportModule(Map<Parameter, String> parameters)
    throws OperationNotSupportedException {
    String pFile = parameters.get(file);
    return new SIARD2ImportModule(Paths.get(pFile)).getDatabaseImportModule();
  }

  @Override
  public DatabaseExportModule buildExportModule(Map<Parameter, String> parameters)
    throws OperationNotSupportedException {
    String pFile = parameters.get(file);

    // optional
    boolean pCompress = Boolean.parseBoolean(compress.valueIfNotSet());
    if (StringUtils.isNotBlank(parameters.get(compress))) {
      pCompress = Boolean.parseBoolean(compress.valueIfSet());
    }

    // optional
    boolean pPrettyPrintXML = Boolean.parseBoolean(prettyPrintXML.valueIfNotSet());
    if (StringUtils.isNotBlank(parameters.get(prettyPrintXML))) {
      pPrettyPrintXML = Boolean.parseBoolean(prettyPrintXML.valueIfSet());
    }

    // optional
    Path pTableFilter = null;
    if (StringUtils.isNotBlank(parameters.get(tableFilter))) {
      pTableFilter = Paths.get(parameters.get(tableFilter));
    }

    // optional
    boolean pExternalLobs = Boolean.parseBoolean(externalLobs.valueIfNotSet());
    if (StringUtils.isNotBlank(parameters.get(externalLobs))) {
      pExternalLobs = Boolean.parseBoolean(externalLobs.valueIfSet());
    }

    // optional
    int pExternalLobsPerFolder = Integer.parseInt(externalLobsPerFolder.valueIfNotSet());
    if (StringUtils.isNotBlank(parameters.get(externalLobsPerFolder))) {
      pExternalLobsPerFolder = Integer.parseInt(parameters.get(externalLobsPerFolder));
      if (pExternalLobsPerFolder <= 0) {
        pExternalLobsPerFolder = Integer.parseInt(externalLobsPerFolder.valueIfNotSet());
      }
    }

    // optional
    long pExternalLobsFolderSize = Long.parseLong(externalLobsFolderSize.valueIfNotSet());
    if (StringUtils.isNotBlank(parameters.get(externalLobsFolderSize))) {
      pExternalLobsFolderSize = Long.parseLong(parameters.get(externalLobsFolderSize));
      if (pExternalLobsFolderSize <= 0) {
        pExternalLobsFolderSize = Long.parseLong(externalLobsFolderSize.valueIfNotSet());
      }
    }

    if (pExternalLobs) {
      return new SIARD2ExportModule(Paths.get(pFile), pCompress, pPrettyPrintXML, pTableFilter, pExternalLobsPerFolder,
        pExternalLobsFolderSize).getDatabaseHandler();
    } else {
      return new SIARD2ExportModule(Paths.get(pFile), pCompress, pPrettyPrintXML, pTableFilter).getDatabaseHandler();
    }
  }
}