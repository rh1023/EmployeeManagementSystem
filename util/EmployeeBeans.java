//EmpoyeeBeans.java
package crud.util;

public class EmployeeBeans {

	// 変数の定義
	private int empId; // 社員ID
	private String name; // 社員名
	private int gender; // 性別（1: 男性、2: 女性）
	private String birthday; // 生年月日（YYYY/MM/DD形式）
	private int deptId; // 部署ID（1: 営業部、2: 経理部、3: 総務部）

	// デフォルトコンストラクタ
	public EmployeeBeans() {
	}

	// 全てのフィールドをセットするコンストラクタ
	public EmployeeBeans(int empId, String name, int gender, String birthday, int deptId) {
		this.empId = empId;
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.deptId = deptId;
	}

	// 各フィールドに対するゲッターとセッターを定義

	// 社員IDのゲッター
	public int getEmpId() {
		return empId;
	}

	// 社員IDのセッター
	public void setEmpId(int empId) {
		this.empId = empId;
	}

	// 社員名のゲッター
	public String getName() {
		return name;
	}

	// 社員名のセッター
	public void setName(String name) {
		this.name = name;
	}

	// 性別のゲッター
	public int getGender() {
		return gender;
	}

	// 性別のセッター
	public void setGender(int gender) {
		this.gender = gender;
	}

	// 生年月日のゲッター
	public String getBirthday() {
		return birthday;
	}

	// 生年月日のセッター
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	// 部署IDのゲッター
	public int getDeptId() {
		return deptId;
	}

	// 部署IDのセッター
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	// 性別を文字列に変換するメソッド
	// 数値の性別を「男性」または「女性」の文字列に変換して返す
	public String getGenderText() {
		return (gender == 1) ? "男性" : "女性";
	}

	// 部署IDを文字列に変換するメソッド
	// 部署IDを「営業部」「経理部」「総務部」の文字列に変換して返す
	public String getDeptName() {
		switch (deptId) {
		case 1:
			return "営業部";
		case 2:
			return "経理部";
		case 3:
			return "総務部";
		default:
			return "不明"; // 該当しない場合は「不明」と表示
		}
	}
}
