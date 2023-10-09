import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

public class ResumeController {
	public static void main(String[] args) {

		Resume resume = new Resume();
		createResume(resume);

		System.out.println(resume.getPersonInfo().toString());
		Workbook wb = new XSSFWorkbook();


		createResumeSheet(wb, resume);

		// Todo 자기소개서를 시트에 입력하는 작업



		try (OutputStream fileOut = new FileOutputStream("이력서.xlsx")) {
			wb.write(fileOut);
		}catch (Exception e){
			e.printStackTrace();
		}

	}
	private static void createResume(Resume resume){
		ResumeView resumeView = new ResumeView();
		System.out.println("이력서 정보를 입력을 시작합니다..");
		resume.setPersonInfo(resumeView.inputPersonInfo());
		resume.setCareerList(resumeView.inputCareerList());
		resume.setEducationList(resumeView.inputEducation());
		resume.setSelfIntroduction(resumeView.inputSelfIntroduction());
	}
	private static void createResumeSheet(Workbook wb, Resume resume) {
		String sheetName = WorkbookUtil.createSafeSheetName("이력서");
		Sheet sheet = wb.createSheet(sheetName);
		String[] personalInfoHeader = {"사진", "이름", "이메일", "주소", "전화번호", "생년월일"};
		String[] educationHeader = {"졸업년도", "학교명", "전공", "졸업여부"};
		String[] careerHeader = {"근무기간", "근무처", "담당업무", "근속연수"};

		// profile   sheet에 작성

		int rowNum=0;
		Row row1 = sheet.createRow(rowNum++);
		for (int i=0;i<personalInfoHeader.length; i++){
			row1.createCell(i).setCellValue(personalInfoHeader[i]);
		}

		Row row2 = sheet.createRow(rowNum++);
		PersonInfo personalInfo = resume.getPersonInfo();
		// cell 0은 사진
		createImageCell(wb, personalInfo.getPhoto());
		row2.createCell(1).setCellValue(personalInfo.getName());
		row2.createCell(2).setCellValue(personalInfo.getEmail());
		row2.createCell(3).setCellValue(personalInfo.getAddress());
		row2.createCell(4).setCellValue(personalInfo.getPhoneNumber());
		row2.createCell(5).setCellValue(personalInfo.getBirthDate());

		// 학력 정보 sheet에 작성
		Row row3 = sheet.createRow(rowNum++);

		for(int i=0; i< educationHeader.length;i++){
			row3.createCell(i).setCellValue(educationHeader[i]);
		}
		List<Education> educationList = resume.getEducationList();

		for(Education education: educationList){
			Row newRow = sheet.createRow(rowNum++);
			newRow.createCell(0).setCellValue(education.getGraduationYear());
			newRow.createCell(1).setCellValue(education.getSchoolName());
			newRow.createCell(2).setCellValue(education.getMajor());
			newRow.createCell(3).setCellValue(education.getGraduationStatus());
		}

		// 경력 정보 sheet에 작성
		Row carrerRow = sheet.createRow(rowNum++);
		for(int i=0; i< careerHeader.length;i++){
			carrerRow.createCell(i).setCellValue(careerHeader[i]);
		}

		List<Career> careerList = resume.getCareerList();
		for(Career career: careerList){
			Row newRow = sheet.createRow(rowNum++);
			newRow.createCell(0).setCellValue(career.getWorkPeriod());
			newRow.createCell(1).setCellValue(career.getCompanyName());
			newRow.createCell(2).setCellValue(career.getJobTitle());
			newRow.createCell(3).setCellValue(career.getEmploymentYears());
		}
	}

	private static void createImageCell(Workbook wb, String photo){
		final double PPM = 2.83465; // PIXELS PER MILLIMETER
		Sheet sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(1);
//		row.setHeightInPoints();
		try (InputStream is = new FileInputStream(photo)) {
//
			byte[] bytes = IOUtils.toByteArray(is);
			int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
			CreationHelper helper = wb.getCreationHelper();
			Drawing drawing = sheet.createDrawingPatriarch();
			//create the drawing patriarch. - top level container for all shapes.
			ClientAnchor anchor = helper.createClientAnchor();
			// set top-left corner of the picture,
			anchor.setCol1(0);
			anchor.setRow1(1);
			anchor.setRow2(2);
			anchor.setCol2(1);
			Picture pict = drawing.createPicture(anchor, pictureIdx);

			int newWidth = (int) (35 * PPM); // mm 단위를 픽셀 단위로 변환합니다 (1mm = 2.83465px).
			int newHeight = (int) (45 * PPM); // mm 단위를 픽셀 단위로 변환합니다 (1mm = 2.83465px).
			row.setHeightInPoints((float) (newHeight * 72) /96);
			int columnWidth = (int) Math.floor(((float) newWidth / (float) 8) * 256);

			sheet.setColumnWidth(0, columnWidth);
//			pict.resize();
		}catch (Exception e){
			e.printStackTrace();
		}

	}
}
