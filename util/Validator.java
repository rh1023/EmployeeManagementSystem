//Validator.java
package crud.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Validator {

	// 社員IDチェックメソッド
	// 入力されたIDが1〜9999の範囲であるか確認し、正しい値が入力されるまでループ
	public static int checkEmployeeId(BufferedReader br) throws IOException {
		int empId;
		while (true) {
			try {
				empId = Integer.parseInt(br.readLine()); // 入力を整数に変換
				if (empId >= 1 && empId <= 9999) { // 範囲チェック
					break; // 正しい値が入力されたらループ終了
				} else {
					System.out.println("1以上9999以下の整数を入力してください：");
				}
			} catch (NumberFormatException e) {
				System.out.println("数値を入力してください：");
			}
		}
		return empId;
	}

	// 社員名チェックメソッド
	// 1文字以上30文字以下の文字列が入力されるまでループ
	public static String checkEmployeeName(BufferedReader br) throws IOException {
		String name;
		while (true) {
			name = br.readLine(); // 入力された文字列を取得
			if (name.length() >= 1 && name.length() <= 30) { // 文字数の範囲チェック
				break; // 正しい文字列が入力されたらループ終了
			} else {
				System.out.println("1文字以上30文字以下の文字列を入力してください：");
			}
		}
		return name;
	}

	// 性別チェックメソッド
	// 1（男性）または2（女性）が入力されるまでループ
	public static int checkGender(BufferedReader br) throws IOException {
		int gender;
		while (true) {
			try {
				gender = Integer.parseInt(br.readLine()); // 入力を整数に変換
				if (gender == 1 || gender == 2) { // 性別が1か2であるかをチェック
					break; // 正しい値が入力されたらループ終了
				} else {
					System.out.println("1または2を入力してください：");
				}
			} catch (NumberFormatException e) {
				System.out.println("数値を入力してください：");
			}
		}
		return gender;
	}

	// 生年月日チェックメソッド
	// "YYYY/MM/DD"形式の日付が入力されるまでループ
	// 入力された日付が実在する日付であるかも厳密にチェック
	public static String checkBirthday(BufferedReader br) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		sdf.setLenient(false); // 日付の厳密なチェックを有効化
		String birthday;
		while (true) {
			birthday = br.readLine(); // 入力された日付を取得
			try {
				Date date = sdf.parse(birthday); // 日付の形式を確認
				break; // 正しい形式ならループ終了
			} catch (ParseException e) {
				System.out.println("正しい形式（西暦年/月/日）で日付を入力してください：");
			}
		}
		return birthday;
	}

	// 部署IDチェックメソッド
	// 1（営業部）から3（総務部）までの整数が入力されるまでループ
	public static int checkDeptId(BufferedReader br) throws IOException {
		int deptId;
		while (true) {
			try {
				deptId = Integer.parseInt(br.readLine()); // 入力を整数に変換
				if (deptId >= 1 && deptId <= 3) { // 部署IDが1〜3であるかをチェック
					break; // 正しい値が入力されたらループ終了
				} else {
					System.out.println("1以上3以下の整数を入力してください：");
				}
			} catch (NumberFormatException e) {
				System.out.println("数値を入力してください：");
			}
		}
		return deptId;
	}
}
