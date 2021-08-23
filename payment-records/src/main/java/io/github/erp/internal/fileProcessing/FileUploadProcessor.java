package io.github.erp.internal.fileProcessing;

import io.github.erp.internal.model.FileNotification;

public interface FileUploadProcessor<T> {

    T processFileUpload(T fileUpload, FileNotification fileNotification);
}
