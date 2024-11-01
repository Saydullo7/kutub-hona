mport java.time.LocalDate
fun main() {
    val library = Library()

    library.addBook(Book("O'tmishdan kelganlar", "A. Qodiriy", "Badiiy"))
    library.addBook(Book("Qora ko'cha", "S. Atoyev", "Detektiv"))
    library.addBook(Book("Kichik Prens", "A. de Saint-Exupéry", "Badiiy"))

    library.listBooks()

    val student1 = Student("Ali", 1)
    val student2 = Student("Olim", 2)

    println(library.borrowBook(student1, "O'tmishdan kelganlar"))
    println(library.borrowBook(student2, "Qora ko'cha"))

    library.listBooks()

    println(library.returnBook(student1, "O'tmishdan kelganlar"))
    println(library.returnBook(student2, "Qora ko'cha"))

    library.listBooks()
}

data class Book(val title: String, val author: String, val genre: String, var isAvailable: Boolean = true)

data class Student(val name: String, val id: Int)

class Library {
    private val books = mutableListOf<Book>()
    private val borrowedBooks = mutableMapOf<Student, MutableList<Book>>()

    fun addBook(book: Book) {
        books.add(book)
    }

    fun borrowBook(student: Student, bookTitle: String): String {
        val book = books.find { it.title == bookTitle && it.isAvailable }
        return if (book != null) {
            book.isAvailable = false
            borrowedBooks.computeIfAbsent(student) { mutableListOf() }.add(book)
            "Kitob olingan: ${book.title}"
        } else {
            "Kitob mavjud emas yoki allaqachon olingan."
        }
    }

    fun returnBook(student: Student, bookTitle: String): String {
        val borrowed = borrowedBooks[student]?.find { it.title == bookTitle }
        return if (borrowed != null) {
            borrowed.isAvailable = true
            borrowedBooks[student]?.remove(borrowed)
            "Kitob qaytarilgan: ${borrowed.title}"
        } else {
            "Sizda bunday kitob yo'q."
        }
    }