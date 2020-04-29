package org.eclipse.fordiac.ide.export.forte_ng.tests;

interface DatatypeConstants {

	static final int INDEX_START = 0;
	static final String LWORD = "LWORD"; //$NON-NLS-1$
	static final int SIZE_LWORD = 64;
	static final String DWORD = "DWORD"; //$NON-NLS-1$
	static final int SIZE_DWORD = 32;
	static final String WORD = "WORD"; //$NON-NLS-1$
	static final int SIZE_WORD = 16;
	static final String BYTE = "BYTE"; //$NON-NLS-1$
	static final int SIZE_BYTE = 8;
	static final String BOOL = "BOOL"; //$NON-NLS-1$
	static final int SIZE_BOOL = 1;

	static final String DINT = "DINT"; //$NON-NLS-1$
	static final String REAL = "REAL"; //$NON-NLS-1$

	static int getSize(String type) {
		switch (type) {
		case LWORD:
			return SIZE_LWORD;
		case DWORD:
			return SIZE_DWORD;
		case WORD:
			return SIZE_WORD;
		case BYTE:
			return SIZE_BYTE;
		case BOOL:
			return SIZE_BOOL;
		default:
			return 0;
		}

	}

	static int indexStop(String sourceType, String accessType) {
		int sourceSize = getSize(sourceType);
		int accessSize = getSize(accessType);
		assert (accessSize != 0);
		return (sourceSize / accessSize) - 1;
	}

}
