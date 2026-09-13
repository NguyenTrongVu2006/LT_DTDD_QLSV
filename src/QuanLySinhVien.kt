data class Student(
    val id: String,
    val fullName: String,
    val age: Int,
    val major: String,
    val gpa: Double
) {
    fun thongTin() {
        println("%-12s | %-22s | %-4d | %-12s | %-5.2f".format(id, fullName, age, major, gpa))
    }
}

val studentList = mutableListOf(
    Student("2401", "Nguyen Quang Hai", 20, "IT", 3.65),
    Student("2501", "Le Minh Duc", 19, "Oto", 7.80),
    Student("2201", "Hoang Duc", 22, "Kien truc", 4.50),
    Student("2601", "Vo Hoang Yen", 18, "IT", 9.10),
    Student("2301", "Nguyen Trong Vu", 21, "IT", 8.90)
)

fun tieuDe() {
    println("----------------------------------------------------------------------")
    println("%-12s | %-22s | %-4s | %-12s | %-5s".format("Student ID", "Full Name", "Age", "Major", "GPA"))
    println("----------------------------------------------------------------------")
}

fun displayStudents(list: List<Student>) {
    if (list.isEmpty()) {
        println("-> Danh sách trống!")
        return
    }
    tieuDe()
    list.forEach { it.thongTin() }
    println()
}

fun addStudent() {
    println("--- THÊM SINH VIÊN MỚI ---")
    print("Nhập ID: ")
    val id = readln().trim()
    if (studentList.any { it.id.equals(id, ignoreCase = true) }) {
        println("-> Lỗi: Mã sinh viên đã tồn tại!")
        return
    }

    print("Nhập Full Name: ")
    val fullName = readln().trim()
    print("Nhập Age: ")
    val age = readln().trim().toIntOrNull()
    if (age == null || age <= 0) {
        println("-> Lỗi: Tuổi không hợp lệ!")
        return
    }
    print("Nhập Major (ví dụ: IT, Kien truc, Oto): ")
    val major = readln().trim()
    print("Nhập GPA (0.0 - 10.0): ")
    val gpa = readln().trim().toDoubleOrNull()
    if (gpa == null || gpa < 0.0 || gpa > 10.0) {
        println("-> Lỗi: GPA không hợp lệ!")
        return
    }
    studentList.add(Student(id, fullName, age, major, gpa))
    println("-> Thêm sinh viên thành công!")
}

fun displayAllStudentsMenu() {
    if (studentList.isEmpty()) {
        println("-> Danh sách sinh viên hiện đang trống!")
        return
    }
    println("------ TÙY CHỌN HIỂN THỊ DANH SÁCH ------")
    println("1. Hiển thị danh sách gốc mặc định")
    println("2. Sắp xếp sinh viên theo GPA giảm dần")
    println("3. Hiển thị 3 sinh viên có GPA cao nhất")
    println("4. Sắp xếp sinh viên theo tuổi tăng dần")
    println("5. Sắp xếp sinh viên theo tên (A -> Z)")
    print("Chọn kiểu hiển thị: ")

    when (readln().trim()) {
        "1" -> {
            println("\nDanh sách sinh viên mặc định:")
            displayStudents(studentList)
        }
        "2" -> {
            println("\nDanh sách theo GPA giảm dần:")
            displayStudents(studentList.sortedByDescending { it.gpa })
        }
        "3" -> {
            println("\nTop 3 sinh viên có GPA cao nhất:")
            displayStudents(studentList.sortedByDescending { it.gpa }.take(3))
        }
        "4" -> {
            println("\nDanh sách sắp xếp theo tuổi tăng dần:")
            displayStudents(studentList.sortedBy { it.age })
        }
        "5" -> {
            println("\nDanh sách sắp xếp theo tên (A -> Z):")
            displayStudents(studentList.sortedBy { it.fullName.trim().split("\\s+".toRegex()).last() })
        }
        else -> println("-> Lựa chọn không hợp lệ!")
    }
}

fun searchStudentMenu() {
    println("\n------ TÌM KIẾM & THỐNG KÊ ------")
    println("1. Tìm sinh viên theo một phần tên")
    println("2. Tìm tất cả sinh viên thuộc một ngành")
    println("3. Tìm sinh viên có GPA nằm trong khoảng 7.0 -> 8.5")
    println("4. Tìm sinh viên lớn tuổi nhất")
    println("5. Đếm số sinh viên có GPA >= 8.0")
    println("6. Đếm số sinh viên có GPA < 5.0")
    print("Chọn chức năng: ")
    when (readln().trim()) {
        "1" -> {
            print("Nhập phần tên cần tìm: ")
            val keyword = readln().trim()
            val matched = studentList.filter { it.fullName.contains(keyword, ignoreCase = true) }
            displayStudents(matched)
        }
        "2" -> {
            print("Nhập tên ngành cần tìm: ")
            val majorInput = readln().trim()
            val matched = studentList.filter { it.major.equals(majorInput, ignoreCase = true) }
            displayStudents(matched)
        }
        "3" -> {
            println("Danh sách sinh viên có GPA từ 7.0 đến 8.5:")
            val matched = studentList.filter { it.gpa in 7.0..8.5 }
            displayStudents(matched)
        }
        "4" -> {
            val maxAge = studentList.maxOfOrNull { it.age }
            if (maxAge != null) {
                println("Sinh viên lớn tuổi nhất ($maxAge tuổi):")
                displayStudents(studentList.filter { it.age == maxAge })
            } else {
                println("-> Danh sách trống!")
            }
        }
        "5" -> {
            val count = studentList.count { it.gpa >= 8.0 }
            println("-> Số sinh viên có GPA >= 8.0: $count sinh viên")
        }
        "6" -> {
            val count = studentList.count { it.gpa < 5.0 }
            println("-> Số sinh viên có GPA < 5.0: $count sinh viên")
        }
        else -> println("-> Lựa chọn không hợp lệ!")
    }
}

fun calculateAverageGPA() {
    if (studentList.isEmpty()) {
        println("-> Danh sách trống!")
        return
    }

    val avgAll = studentList.map { it.gpa }.average()
    println("\n-> GPA trung bình của toàn bộ sinh viên: %.2f".format(avgAll))
    print("Nhập tên ngành cần tính GPA trung bình: ")
    val majorInput = readln().trim()

    val danhSachTheoNganh = studentList.filter { it.major.equals(majorInput, ignoreCase = true) }
    if (danhSachTheoNganh.isNotEmpty()) {
        val avgMajor = danhSachTheoNganh.map { it.gpa }.average()
        println("-> GPA trung bình của ngành '$majorInput': %.2f (Dựa trên %d sinh viên)".format(avgMajor, danhSachTheoNganh.size))
    } else {
        println("-> Không tìm thấy sinh viên nào thuộc ngành '$majorInput'!")
    }
}

fun findHighestGPA() {
    if (studentList.isEmpty()) {
        println("-> Danh sách trống!")
        return
    }
    val highestGPA = studentList.maxOf { it.gpa }
    val topStudents = studentList.filter { it.gpa == highestGPA }

    println("\n-> Sinh viên có GPA cao nhất (%.2f):".format(highestGPA))
    displayStudents(topStudents)
}

fun removeStudent() {
    print("\nNhập Student ID cần xóa: ")
    val id = readln().trim()
    val removed = studentList.removeIf { it.id.equals(id, ignoreCase = true) }
    if (removed) {
        println("-> Đã xóa sinh viên có ID: $id")
    } else {
        println("-> Không tìm thấy sinh viên có ID: $id")
    }
}

fun main() {
    while (true) {
        println(
            """          
            ========== STUDENT MANAGEMENT ==========
            1. Add student
            2. Display all students
            3. Search student
            4. Calculate average GPA
            5. Find student with highest GPA
            6. Remove student
            0. Exit
            ========================================
            """.trimIndent()
        )
        print("Choose: ")
        when (readln().trim()) {
            "1" -> addStudent()
            "2" -> displayAllStudentsMenu()
            "3" -> searchStudentMenu()
            "4" -> calculateAverageGPA()
            "5" -> findHighestGPA()
            "6" -> removeStudent()
            "0" -> {
                println("Đã thoát chương trình.")
                break
            }
            else -> println("-> Lựa chọn không hợp lệ, vui lòng chọn lại!")
        }
    }
}