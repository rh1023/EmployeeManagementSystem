//Employee.java
package crud.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import crud.db.EmployeeDao;
import crud.util.EmployeeBeans;
import crud.util.Validator;

public class Employee {

	public static void main(String[] args) throws IOException {
		// 標準入力の準備
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		EmployeeDao dao = new EmployeeDao(); // データベース操作用のDAOオブジェクトを生成
		String choice = ""; // ユーザーの選択を保存するための変数

		// メニューを繰り返し表示するループ（7番を選ぶまでループする）
		while (!choice.equals("7")) {
			// メニュー表示
			System.out.println("＝＝＝社員管理システム＝＝＝");
			System.out.println("1.一覧表示");
			System.out.println("2.社員名検索");
			System.out.println("3.部署ID検索");
			System.out.println("4.登録");
			System.out.println("5.更新");
			System.out.println("6.削除");
			System.out.println("7.終了");
			System.out.print("メニュー番号を入力してください：");
			choice = br.readLine(); // ユーザー入力を取得
			System.out.println();

			// ユーザーの選択に応じた処理を実行
			switch (choice) {
			// 1. 一覧表示
			case "1":
				// 全社員の情報を取得し、表示
				List<EmployeeBeans> employees = dao.getAllEmployees();
				System.out.printf("%-5s %-7s %-4s %-8s %-10s\n", "社員ID", "社員名", "性別", "生年月日", "部署");
				for (EmployeeBeans emp : employees) {
					// 各社員の情報を整形して表示
					System.out.printf("%-7d %-6s %-4s %-12s %-10s\n", emp.getEmpId(), emp.getName(),
							emp.getGenderText(), emp.getBirthday(), emp.getDeptName());
				}
				break;

			// 2. 社員名検索
			case "2":
				// 社員名で部分一致検索
				System.out.print("社員名を入力してください： ");
				String searchName = Validator.checkEmployeeName(br); // 社員名の入力チェック
				List<EmployeeBeans> nameResults = dao.searchByName(searchName); // 結果取得
				System.out.printf("%-5s %-7s %-4s %-8s %-10s\n", "社員ID", "社員名", "性別", "生年月日", "部署");
				for (EmployeeBeans emp : nameResults) {
					// 検索結果を表示
					System.out.printf("%-7d %-6s %-4s %-12s %-10s\n", emp.getEmpId(), emp.getName(),
							emp.getGenderText(), emp.getBirthday(), emp.getDeptName());
				}
				break;

			// 3. 部署ID検索
			case "3":
				// 部署IDによる検索
				System.out.print("部署ID(1：営業部、2：経理部、3：総務部)を入力してください： ");
				int searchDeptId = Validator.checkDeptId(br); // 部署IDの入力チェック
				List<EmployeeBeans> deptResults = dao.searchByDeptId(searchDeptId);
				if (deptResults.isEmpty()) {
					// 該当する社員がいない場合の処理
					System.out.println("該当する部署IDの社員が存在しません。");
				} else {
					System.out.printf("%-5s %-7s %-4s %-8s %-10s\n", "社員ID", "社員名", "性別", "生年月日", "部署");
					for (EmployeeBeans emp : deptResults) {
						// 検索結果を表示
						System.out.printf("%-7d %-6s %-4s %-12s %-10s\n", emp.getEmpId(), emp.getName(),
								emp.getGenderText(), emp.getBirthday(), emp.getDeptName());
					}
				}
				break;

			// 4. 社員登録
			case "4":
				// 新規社員登録の処理
				System.out.print("社員名：");
				String name = Validator.checkEmployeeName(br); // 社員名の入力チェック
				System.out.print("性別（1：男性、2：女性）：");
				int gender = Validator.checkGender(br); // 性別の入力チェック
				System.out.print("生年月日（西暦年/月/日）：");
				String birthday = Validator.checkBirthday(br); // 生年月日の入力チェック
				System.out.print("部署ID（1：営業部、2：経理部、3：総務部）：");
				int deptId = Validator.checkDeptId(br); // 部署IDの入力チェック

				// 新規社員情報の生成
				EmployeeBeans newEmployee = new EmployeeBeans();
				newEmployee.setName(name);
				newEmployee.setGender(gender);
				newEmployee.setBirthday(birthday);
				newEmployee.setDeptId(deptId);

				// 社員情報の登録
				dao.addEmployee(newEmployee);
				break;

			// 5. 部分更新
			case "5":
				// 更新する社員のIDを取得
				System.out.print("更新する社員の社員IDを入力してください: ");
				int empIdUpdate = Validator.checkEmployeeId(br); // 社員IDの入力チェック

				// 更新する社員のオブジェクトを作成
				EmployeeBeans updatedEmployee = new EmployeeBeans();
				updatedEmployee.setEmpId(empIdUpdate);

				// 各項目を確認し、変更がある場合のみ更新
				System.out.print("更新する社員名（変更がない場合はEnterキーを押してください）：");
				String newName = br.readLine();
				if (!newName.isEmpty()) {
					updatedEmployee.setName(newName); // 新しい名前をセット
				}

				System.out.print("更新する性別（1: 男性, 2: 女性, 変更がない場合はEnterキーを押してください）：");
				String newGender = br.readLine();
				if (!newGender.isEmpty()) {
					updatedEmployee.setGender(Integer.parseInt(newGender)); // 新しい性別をセット
				}

				System.out.print("更新する生年月日（YYYY/MM/DD形式、変更がない場合はEnterキーを押してください）：");
				String newBirthday = br.readLine();
				if (!newBirthday.isEmpty()) {
					updatedEmployee.setBirthday(newBirthday); // 新しい生年月日をセット
				}

				System.out.print("更新する部署ID（1: 営業部, 2: 経理部, 3: 総務部, 変更がない場合はEnterキーを押してください）：");
				String newDeptId = br.readLine();
				if (!newDeptId.isEmpty()) {
					updatedEmployee.setDeptId(Integer.parseInt(newDeptId)); // 新しい部署IDをセット
				}

				// 社員情報の部分更新
				dao.updateEmployeePartially(updatedEmployee);
				break;

			// 6. 社員削除
			case "6":
				// 削除する社員のIDを入力させる
				System.out.print("削除する社員のIDを入力してください: ");
				int empIdDelete = Validator.checkEmployeeId(br); // 社員IDの入力チェック
				dao.deleteEmployee(empIdDelete); // 社員情報を削除
				break;

			// 7. システム終了
			case "7":
				System.out.println("システムを終了します。");
				break;

			// 無効な入力
			default:
				System.out.println("***** 無効な入力です *****");
			}

			System.out.println(); // 改行を追加して見やすくする
		}
	}
}
