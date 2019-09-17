package project.com.tripinsite.helper;

/**
 * 업로드 된 파일의 정보를 저장하기 위한 JavaBeans - 이 클래스의 객체가 업로드 된 파일의 수 만큼 생성되어 List 형태로
 * 보관된다.
 */
public class FileInfo {
	private String originName; // 원본 파일 이름
	private String fileDir; // 파일이 저장되어 있는 서버상의 경로
	private String fileName; // 서버상의 파일 이름
	private String contentType; // 파일의 형식
	private long fileSize; // 파일의 용량

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public String toString() {
		return "FileInfo [originName=" + originName + ", fileDir=" + fileDir + ", fileName=" + fileName
				+ ", contentType=" + contentType + ", fileSize=" + fileSize + "]";
	}

}