import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubjectManager<T extends Subject> {
    private List<T> list = new ArrayList<>();
    public void add(T subject) {
        list.add(subject);
        System.out.println("Thêm môn học thành công!");
    }

    public void display() {
        System.out.println("\n--- DANH SÁCH MÔN HỌC ---");
        if (list.isEmpty()) {
            System.out.println("(Danh sách trống)");
        } else {
            list.forEach(System.out::println);
        }
    }

    public void delete(String code) {
        boolean removed = list.removeIf(s -> s.getCode().equalsIgnoreCase(code));
        if (removed) {
            System.out.println("Đã xóa môn học có mã: " + code);
        } else {
            System.err.println("Lỗi: Không tìm thấy môn học mã " + code);
        }
    }

    public Optional<T> findByName(String name) {
        return list.stream()
                .filter(s -> s.getName().toLowerCase().contains(name.toLowerCase()))
                .findFirst(); 
    }

    public void filterByCredits() {
        System.out.println("\n--- CÁC MÔN HỌC TRÊN 3 TÍN CHỈ ---");
        long count = list.stream()
                .filter(s -> s.getCredits() > 3)
                .peek(System.out::println)
                .count();

        if (count == 0) {
            System.out.println("Không có môn nào > 3 tín chỉ.");
        }
    }

    public boolean isCodeExist(String code) {
        return list.stream().anyMatch(s -> s.getCode().equalsIgnoreCase(code));
    }
}
