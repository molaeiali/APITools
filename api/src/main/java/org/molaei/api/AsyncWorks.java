package org.molaei.api;

public interface AsyncWorks {
    void preExecute();
    void postExecute(String jsonToParse);
}
