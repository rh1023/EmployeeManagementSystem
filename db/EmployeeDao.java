//EmployeeDao.java
package crud.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import crud.util.EmployeeBeans;

public class EmployeeDao {

	// 社員一覧の取得メソッド
	// データベースから全社員の情報を取得し、Listに格納して返す
	public List<EmployeeBeans> getAllEmployees() {
		List<EmployeeBeans> employees = new ArrayList<>();
		String sql = "SELECT * FROM employee";

		try (Connection conn = EmpDBCon.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			// 結果セットから各行の社員情報を取得し、リストに追加
			while (rs.next()) {
				employees.add(new EmployeeBeans(
						rs.getInt("emp_id"),
						rs.getString("emp_name"),
						rs.getInt("gender"), // 性別は整数値のまま取得
						rs.getString("birthday"),
						rs.getInt("dept_id")));
			}
		} catch (SQLException e) {
			System.out.println("社員一覧の取得に失敗しました。");
		}
		return employees;
	}

	// 社員情報を表示するメソッド
	// 性別と部署は、それぞれgetGenderText()とgetDeptName()メソッドで変換して表示
	public void displayAllEmployees() {
		List<EmployeeBeans> employees = getAllEmployees();
		for (EmployeeBeans emp : employees) {
			System.out.println("ID: " + emp.getEmpId() + ", 名前: " + emp.getName() + ", 性別: " + emp.getGenderText()
					+ ", 生年月日: " + emp.getBirthday() + ", 部署: " + emp.getDeptName());
		}
	}

	// 社員登録メソッド
	// 新規社員情報をデータベースに登録する
	public void addEmployee(EmployeeBeans employee) {
		String sql = "INSERT INTO employee (emp_name, gender, birthday, dept_id) VALUES (?, ?, ?, ?)";

		try (Connection conn = EmpDBCon.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// プレースホルダーに社員情報をセット
			pstmt.setString(1, employee.getName());
			pstmt.setInt(2, employee.getGender()); // 性別は数値としてセット
			pstmt.setString(3, employee.getBirthday());
			pstmt.setInt(4, employee.getDeptId()); // 部署IDは数値としてセット
			pstmt.executeUpdate();
			System.out.println("社員情報を登録しました。");
		} catch (SQLException e) {
			System.out.println("社員情報の登録に失敗しました。");
			e.printStackTrace();
		}
	}

	// 社員情報の更新メソッド
	// 既存社員の情報を更新する
	public void updateEmployee(EmployeeBeans employee) {
		String sql = "UPDATE employee SET emp_name = ?, gender = ?, birthday = ?, dept_id = ? WHERE emp_id = ?";

		try (Connection conn = EmpDBCon.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// 更新する社員情報をセット
			pstmt.setString(1, employee.getName());
			pstmt.setInt(2, employee.getGender()); // 性別を数値でセット
			pstmt.setString(3, employee.getBirthday());
			pstmt.setInt(4, employee.getDeptId()); // 部署IDを数値でセット
			pstmt.setInt(5, employee.getEmpId()); // 社員IDで更新対象を指定
			pstmt.executeUpdate();
			System.out.println("社員情報が更新されました。");
		} catch (SQLException e) {
			System.out.println("社員情報の更新に失敗しました。");
			e.printStackTrace();
		}
	}

	// 社員削除メソッド
	// 指定された社員IDの社員情報をデータベースから削除する
	public void deleteEmployee(int empId) {
		String sql = "DELETE FROM employee WHERE emp_id = ?";

		try (Connection conn = EmpDBCon.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, empId); // 削除する社員のIDをセット
			int rowsAffected = pstmt.executeUpdate(); // 実行

			if (rowsAffected > 0) {
				System.out.println("社員情報を削除しました。");
			} else {
				System.out.println("指定された社員IDの社員が見つかりませんでした。");
			}
		} catch (SQLException e) {
			System.out.println("社員情報の削除に失敗しました。");
			e.printStackTrace();
		}
	}

	// 追加機能1: 社員名で検索するメソッド
	// 部分一致検索で社員名を含む社員情報を取得する
	public List<EmployeeBeans> searchByName(String name) {
		List<EmployeeBeans> employees = new ArrayList<>();
		String sql = "SELECT * FROM employee WHERE emp_name LIKE ?";

		try (Connection conn = EmpDBCon.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, "%" + name + "%"); // 部分一致検索のためにワイルドカードを使用
			ResultSet rs = pstmt.executeQuery();

			// 検索結果をリストに追加
			while (rs.next()) {
				employees.add(new EmployeeBeans(
						rs.getInt("emp_id"),
						rs.getString("emp_name"),
						rs.getInt("gender"),
						rs.getString("birthday"),
						rs.getInt("dept_id")));
			}
		} catch (SQLException e) {
			System.out.println("社員名検索に失敗しました。");
			e.printStackTrace();
		}
		return employees;
	}

	// 追加機能2: 部署IDで検索するメソッド
	// 指定された部署IDに属する社員情報を取得する
	public List<EmployeeBeans> searchByDeptId(int deptId) {
		List<EmployeeBeans> employees = new ArrayList<>();
		String sql = "SELECT * FROM employee WHERE dept_id = ?";

		try (Connection conn = EmpDBCon.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, deptId); // 部署IDを条件にセット
			ResultSet rs = pstmt.executeQuery();

			// 検索結果をリストに追加
			while (rs.next()) {
				employees.add(new EmployeeBeans(
						rs.getInt("emp_id"),
						rs.getString("emp_name"),
						rs.getInt("gender"),
						rs.getString("birthday"),
						rs.getInt("dept_id")));
			}
		} catch (SQLException e) {
			System.out.println("部署ID検索に失敗しました。");
			e.printStackTrace();
		}
		return employees;
	}

	// 追加機能3: 社員情報を部分的に更新するメソッド
	// 更新対象項目のみを部分的に更新する
	public void updateEmployeePartially(EmployeeBeans employee) {
		StringBuilder sql = new StringBuilder("UPDATE employee SET ");
		boolean firstField = true;

		// 入力された項目のみ更新するため、SQL文を動的に構築
		if (employee.getName() != null && !employee.getName().isEmpty()) {
			if (!firstField)
				sql.append(", ");
			sql.append("emp_name = ?");
			firstField = false;
		}
		if (employee.getGender() != 0) {
			if (!firstField)
				sql.append(", ");
			sql.append("gender = ?");
			firstField = false;
		}
		if (employee.getBirthday() != null && !employee.getBirthday().isEmpty()) {
			if (!firstField)
				sql.append(", ");
			sql.append("birthday = ?");
			firstField = false;
		}
		if (employee.getDeptId() != 0) {
			if (!firstField)
				sql.append(", ");
			sql.append("dept_id = ?");
		}
		sql.append(" WHERE emp_id = ?"); // 更新対象の社員IDを指定

		try (Connection conn = EmpDBCon.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

			int paramIndex = 1;

			// 入力があった項目のみプレースホルダーに値をセット
			if (employee.getName() != null && !employee.getName().isEmpty()) {
				pstmt.setString(paramIndex++, employee.getName());
			}
			if (employee.getGender() != 0) {
				pstmt.setInt(paramIndex++, employee.getGender());
			}
			if (employee.getBirthday() != null && !employee.getBirthday().isEmpty()) {
				pstmt.setString(paramIndex++, employee.getBirthday());
			}
			if (employee.getDeptId() != 0) {
				pstmt.setInt(paramIndex++, employee.getDeptId());
			}
			pstmt.setInt(paramIndex, employee.getEmpId()); // 社員IDをセット
			pstmt.executeUpdate();
			System.out.println("社員情報が更新されました。");

		} catch (SQLException e) {
			System.out.println("社員情報の更新に失敗しました。");
			e.printStackTrace();
		}
	}
}
