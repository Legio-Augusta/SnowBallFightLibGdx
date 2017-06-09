package de.swagner.sbf2.sgh_900.microedition.rms;

/**
 * Created by Admin on 6/9/2017.
 */

public class RecordStore {

    public static RecordStore openRecordStore(String recordStoreName, boolean createIfNecessary) {

        return new RecordStore();
    }

    public static RecordStore openRecordStore(String recordStoreName, boolean createIfNecessary, int authmode, boolean writable) {
        return new RecordStore();
    }

    public int getNumRecords() {
        return 0;
    }

    public int addRecord(byte[] data, int offset, int numBytes) {
        return 1;
    }

    public void setRecord(int recordId, byte[] newData, int offset, int numBytes) {

    }

    public void closeRecordStore() {

    }

    public byte[] getRecord(int recordId) {
        byte[] data = new byte[1];
        return data;
    }
}
