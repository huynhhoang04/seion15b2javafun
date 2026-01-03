import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static SubjectManager<Subject> manager = new SubjectManager<>();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        int choice = 0;
        do {
            System.out.println("\n========= QUẢN LÝ MÔN HỌC =========");
            System.out.println("1. Hiển thị danh sách môn học");
            System.out.println("2. Thêm môn học mới");
            System.out.println("3. Xóa môn học theo mã");
            System.out.println("4. Tìm kiếm môn học theo tên");
            System.out.println("5. Lọc môn học có tín chỉ > 3");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số!");
                choice = -1;
            }

            switch (choice) {
                case 1:
                    manager.display();
                    break;
                case 2:
                    addSubject();
                    break;
                case 3:
                    deleteSubject();
                    break;
                case 4:
                    searchSubject();
                    break;
                case 5:
                    manager.filterByCredits();
                    break;
                case 0:
                    System.out.println("Thoát chương trình.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 0);
    }

    private static void addSubject() {
        try {
            System.out.print("Nhập mã môn học: ");
            String code = scanner.nextLine();
            if (manager.isCodeExist(code)) {
                System.err.println("Mã môn học đã tồn tại!");
                return;
            }

            System.out.print("Nhập tên môn học: ");
            String name = scanner.nextLine();

            System.out.print("Nhập số tín chỉ (1-10): ");
            int credits = Integer.parseInt(scanner.nextLine());
            
            if (credits <= 0 || credits > 10) {
                throw new IllegalArgumentException("Số tín chỉ không hợp lệ (Phải từ 1 đến 10)");
            }

            System.out.print("Nhập ngày bắt đầu (dd/MM/yyyy): ");
            String dateStr = scanner.nextLine();
            LocalDate startDate = LocalDate.parse(dateStr, dateFormatter);

            Subject sj = new Subject(code, name, credits, startDate);
            manager.add(sj);

        } catch (NumberFormatException e) {
            System.err.println("Lỗi: Bạn đã nhập chữ thay vì số!");
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi nghiệp vụ: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.err.println("Lỗi: Ngày tháng sai định dạng (Yêu cầu: dd/MM/yyyy)");
        } catch (Exception e) {
            System.err.println("Lỗi không xác định: " + e.getMessage());
        }
    }

    private static void deleteSubject() {
        System.out.print("Nhập mã môn cần xóa: ");
        String code = scanner.nextLine();
        manager.delete(code);
    }

    private static void searchSubject() {
        System.out.print("Nhập tên môn học cần tìm: ");
        String name = scanner.nextLine();
        
        Optional<Subject> result = manager.findByName(name);

        if (result.isPresent()) {
            System.out.println("Kết quả tìm thấy: ");
            System.out.println(result.get());
        } else {
            System.out.println("Không có môn học phù hợp với từ khóa: " + name);
        }
    }
}
