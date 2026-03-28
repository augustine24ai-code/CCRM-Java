package edu.ccrm.util;

import edu.ccrm.service.DataStore;

// Demonstrates creating and using an interface.
@FunctionalInterface // This is a good practice for single-method interfaces
public interface ReportGenerator {
    String generateReport(DataStore dataStore);
}