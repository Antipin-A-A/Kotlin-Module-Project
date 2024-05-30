import kotlin.system.exitProcess

object Object {
    private val mutableArchive = mutableMapOf<Int, Archive>()
    private var archiveNumber : Int? = null
    fun checkArchives() {
        println(" ")
        menuArchive()
        val number = readln().toIntOrNull() ?: return checkArchives()
        when (number) {
            1 -> createArchive() /*Создать Архив*/
            2 -> if (mutableArchive.isEmpty()) {
                println("Архив еще не создан")
                checkArchives()
            } else {
                for (i in mutableArchive) {
                    println(" ${i.key} - ${i.value.title}")
                }
                println("Выберите архив")
                val numberArchive = readln().toIntOrNull() ?: return checkArchives()
                if (numberArchive <= 0 || numberArchive > mutableArchive.size) {
                    println("Такого номера нет")
                    checkArchives()
                } else {
                    checkNotes(numberArchive)
                }
            }

            3 -> exitProcess(3)
            else -> {
                println("Такого пункта нет")
                checkArchives()
            }
        }
    }

    fun createArchive() {
        println("Введите название архива")
        val string = readlnOrNull() ?: return checkArchives()
        if (string.trim().isEmpty()) {
            createArchive()
        } else {
            val put = mutableArchive.put(mutableArchive.size + 1, Archive(string))
            println("архив $string создана")
            checkArchives()
        }
    }

    fun checkNotes(int : Int?) {
        println("Архив - ${mutableArchive[int]!!.title}")
        menuNote()
        val n = readln().toIntOrNull() ?: return checkNotes(int)
        when (n) {
            1 -> createNote(int)
            2 -> if (mutableArchive[int]!!.map.keys.isEmpty()) {// поправил
                println("Заметок нет")
                println(" ")
                checkNotes(int)
            } else {
                readNote()
            }

            3 -> checkArchives()
            else -> {
                println("Такого пункта нет")
                println("")
                checkNotes(int)
            }
        }
    }

    fun createNote(int : Int?) {
        archiveNumber = int
        println("Введите название заметки")
        val string = readlnOrNull()
        val string1 = checkingString(string.toString())
        println("Введите текст заметки")
        val text = readlnOrNull()
        val text1 = checkingString(text.toString())
        val keyMapNumber = mutableArchive[archiveNumber]?.map?.size
        val put =
                mutableArchive[archiveNumber]?.archiveAddMap(
                    keyMapNumber!!,
                    Note(string1, text1)
                )
        println("заметка $string1 добавлена в архив ${mutableArchive[int]!!.title}")
        checkNotes(int)
    }

    fun readNote() {
        println(" ")
        println("Архив ${mutableArchive[archiveNumber]?.title}")
        mutableArchive[archiveNumber]?.getNot().toString()
        println(" ")
        println("Выберите пункт меню")
        menuFoNote()
        val number = readln().toIntOrNull() ?: return readNote()
        when (number) {
            1 -> {
                println("Выберите заметку")
                mutableArchive[archiveNumber]?.getNot().toString()
                val numberNote = readln().toIntOrNull() ?: return readNote()
                if (numberNote <= 0 || numberNote > mutableArchive[archiveNumber]?.map?.keys?.size!!) {
                    println("Такого номера нет")
                    readNote()
                } else {
                    println("Заметка - ${mutableArchive[archiveNumber]?.map?.get(numberNote - 1)?.title}")
                    println("Текст - ${mutableArchive[archiveNumber]?.map?.get(numberNote - 1)?.content}")
                    Thread.sleep(3000)
                    println(" ")
                    readNote()
                }
            }

            2 -> checkNotes(archiveNumber)
            else -> {
                println("Такого пункта нет")
                readNote()
            }
        }
    }


    tailrec fun checkingString(string : String) : String {
        return if (string.trim().isNotEmpty()) {
            string
        } else {
            println("Введите текст")
            val string1 = readln()
            checkingString(string1)
        }
    }
}

