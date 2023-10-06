import com.itextpdf.commons.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ResumeView {
	private final Scanner scanner = new Scanner (System.in);
	public PersonInfo inputPersonInfo(){
		PersonInfo personInfo = new PersonInfo();

		System.out.println("개인 정보를 입력해주세요");
		System.out.print("사진: ");
		personInfo.setPhoto(scanner.nextLine());
		System.out.print("이름: ");
		personInfo.setName(scanner.nextLine());
		System.out.print("email: ");
		personInfo.setEmail(scanner.nextLine());
		System.out.print("주소: ");
		personInfo.setAddress(scanner.nextLine());
		System.out.print("휴대폰: ");
		personInfo.setPhoneNumber(scanner.nextLine());
		System.out.print("생년월일(YYYY-mm-dd): ");
		personInfo.setBirthDate(scanner.nextLine());

		return personInfo;
	}
	public List<Education> inputEducation(){
		List<Education> eduList = new ArrayList<>();

		while(true){
			Education eduInfo = new Education();

			System.out.print("학교 이름: ");
			eduInfo.setSchoolName(scanner.nextLine());

			System.out.print("전공: ");
			eduInfo.setMajor(scanner.nextLine());

			System.out.print("졸업 여부");
			eduInfo.setGraduationStatus(inputGraduationStatus());
			if (!eduInfo.getGraduationStatus().equals("중퇴")) {
				System.out.print("졸업 연도: ");
				eduInfo.setGraduationYear(scanner.nextLine());
			}
			eduList.add(eduInfo);

			System.out.println("더 입력하시겠습니까?(y/n): ");
			String answer = scanner.nextLine();

			if(answer.equals("n") || answer.equals("N")){
				break;
			}
		}
		return eduList;
	}

	public List<Career> inputCareerList(){
		List<Career> careerList = new ArrayList<>();

		while(true){
			Career career = new Career();

			System.out.println("경력 정보를 입력하세요");
			System.out.print("회사 이름: ");
			career.setCompanyName(scanner.nextLine());

			System.out.print("직책: ");
			career.setJobTitle(scanner.nextLine());

			System.out.print("입사 연도: ");
			career.setEmploymentYears(scanner.nextLine());

			System.out.print("재직 기간: ");
			career.setWorkPeriod(scanner.nextLine());

			careerList.add(career);

			System.out.println("경력 정보를 더 입력 하시겠습니까?(y/n):" );
			String answer = scanner.nextLine();
			if(answer.equals("n") || answer.equals("N")){
				break;
			}
		}
		return careerList;
	}

	public String inputSelfIntroduction(){
		StringBuilder selfIntroduction = new StringBuilder();

		System.out.println("자기 소개서를 입력해주세요.(엔터 두 번 치면 종료) \n");
		while(true){
			String newLine = scanner.nextLine();
			if (newLine.isEmpty()){
				break;
			}
			selfIntroduction.append(newLine);
		}

		return selfIntroduction.toString();
	}

	private String inputGraduationStatus(){
		String answer;
		String[] choices = {"졸업", "졸업 예정", "중퇴", "수료"};
		while(true) {
			System.out.println("졸업 상태를 입력해주세요[1-4]");
			System.out.println("[1]졸업 [2]졸업 예정 [3]중퇴 [4]수료");
			int input = scanner.nextInt();
			scanner.nextLine(); // 버퍼 비우기.
			if (input >= 1 && input <= 4) {
				return choices[input-1];
			}
			System.out.println("잘못된 입력입니다");

		}// end while
	}

	private String inputEmail(){
		final String pattern = "\\w+@\\w+\\.\\w+(\\.\\w+)?";

		while(true){
			System.out.print("이메일을 입력하세요: ");
			String email=scanner.nextLine();
			if (Pattern.matches(pattern, email)){
				return email;
			}

			System.out.println("올바른 이메일이 아닙니다.");
		}
	}
}
