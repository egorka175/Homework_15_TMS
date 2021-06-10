package com.tms.Homework_15_TMS;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
public class Home extends Document{
    //Метод, который находит емейл в файле и проверяет его на валидность.
    private static String  gmailRead(File file){
        String rez=null;
        Pattern readGmail=Pattern.compile("^([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})$");
        try(BufferedReader br=new BufferedReader(new FileReader(file))){
            String docOneLine;
            while ((docOneLine=br.readLine())!=null){
                Matcher gmailMatcher=readGmail.matcher(docOneLine);
                  if(gmailMatcher.find()){
                   rez="Емейл: "+docOneLine.substring(gmailMatcher.start(), gmailMatcher.end());
                }
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return rez;
    }
    //Метод, который находит номер мобильного телефона и проверяет его на валидность.
    private static String  mobNumRead(File file){
        String rez2=null;
        Pattern readMobNum=Pattern.compile("^\\+\\d{3}\\-\\(\\d{2}\\)\\-\\d{3}\\-\\d{2}\\-\\d{2}$");
        try(BufferedReader br=new BufferedReader(new FileReader(file))){
            String docOneLine;
            while ((docOneLine=br.readLine())!=null){
                Matcher mobNumMatcher=readMobNum.matcher(docOneLine);
                if(mobNumMatcher.find()){
                    rez2="Номер мобильного телефона: "+docOneLine.substring(mobNumMatcher.start(), mobNumMatcher.end());
                }
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return rez2;
    }
    //Метод, который находит все номера документов и проверяет их на валидность.
    private static List<String> docNumRead(File file){
        List<String> rez2=new ArrayList<>();
        Pattern readNumDoc=Pattern.compile("\\d{4}[-][a-zа-я]{3}[-]\\d{4}[-][a-zа-я]{3}[-]\\d[a-zа-я]\\d[a-zа-я]", Pattern.CASE_INSENSITIVE);
        try(BufferedReader br=new BufferedReader(new FileReader(file))){
            String docOneLine;
            while ((docOneLine=br.readLine())!=null){
                Matcher docNumMatcher=readNumDoc.matcher(docOneLine);
                if(docNumMatcher.find()){
                    rez2.add("Емейл: "+docOneLine.substring(docNumMatcher.start(), docNumMatcher.end()));
                }
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return rez2;
    }
    //Метод, который проверяет папку и расширение файлов на валидность
    //и возвращает сколько документов невалидного формата было обнаруженно.
    private static void validationCheck( List<File> collectionToWrite) {
        int i=0;
        Scanner scan2 = new Scanner(System.in);
        System.out.println("_________________________________________________________________");
        System.out.println("Здравствуйте, товарищ преподаватель!");
        System.out.print("Введите путь к директорию: ");
        String path = scan2.nextLine();
        System.out.println("_________________________________________________________________");
        File file = new File(path);
        if (file.isDirectory() && file.list().length != 0) {
            File[] files = file.listFiles();
            for (File item : files) {
                if (item.getName().endsWith(".txt")) {
                    collectionToWrite.add(item);
                } else {
                    i++;
                    System.out.println("Файл- " + item.getName() + " не является текстовым документом");
                    System.out.println("_________________________________________________________________");
                }
            }
        } else {
            System.out.println("Данная папка пуста!");
            System.out.println("_________________________________________________________________");
        }
        System.out.println("Колличество обработаных докуметы невалидного формата: "+i);

    }
    //Метод, который записывает всю информацию из файлов в коллекцию
    // и возврвщает сколько документов было обработанно.
    private static void writeToCollection(List<File> collectionOfPaths, Map<String,Document> collectionToWrite) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Введите количество файлов которые желаете обработать: ");
        int N = scan.nextInt();
        List<File> numberOfFiles = collectionOfPaths.stream()
                .limit(N)
                .collect(Collectors.toList());
        for (File item2 : numberOfFiles) {
            Document g = new Document();
            g.setDocNum(docNumRead(item2));
            g.setMobNum(mobNumRead(item2));
            g.setEmail(gmailRead(item2));
            String foo = item2.getName();
            foo = foo.substring(0, foo.lastIndexOf('.'));
            collectionToWrite.put(foo, new Document(g.getDocNum(), g.getMobNum(), g.getEmail()));
        }
        System.out.println("Колличество документов валидного формата: "+collectionToWrite.size());
    }
    //Runner.
    public static void main(String[] args) {
        Map<String, Document> informationFile=new HashMap<>();
        List<File> pathFileArray=new ArrayList<>();
        validationCheck(pathFileArray);
        writeToCollection(pathFileArray,informationFile);
        System.out.println("_________________________________________________________________");
    }
}




