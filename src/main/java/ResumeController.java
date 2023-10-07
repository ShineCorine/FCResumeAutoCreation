public class ResumeController {
	public static void main(String[] args) {

		Resume resume = new Resume();
		createResume(resume);

		System.out.println(resume.getPersonInfo().toString());
		//Todo 이력서정보를 엑셀 시트에 입력하는 작업


		// Todo 자기소개서를 시트에 입력하는 작업

		// 엑셀 파일로 저장하기



	}
	public static void createResume(Resume resume){
		ResumeView resumeView = new ResumeView();
		System.out.println("이력서 정보를 입력을 시작합니다..");
		resume.setPersonInfo(resumeView.inputPersonInfo());
		resume.setCareerList(resumeView.inputCareerList());
		resume.setEducationList(resumeView.inputEducation());
		resume.setSelfIntroduction(resumeView.inputSelfIntroduction());
	}
}
