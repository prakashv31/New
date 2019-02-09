package com.core.transaction.application.processor;

public enum FileTypeEnum {
		
		CSV("CSV"), XML("XML");

        private String fileType;

        private FileTypeEnum(String fileType) {
            this.fileType = fileType;
        }

        public String getFileType() {
            return this.fileType;
        }
}
