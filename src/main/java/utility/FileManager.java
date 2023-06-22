package utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import data.*;
import exceptions.WrongAmountOfElementsException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Random;


/**
 * Operates the file for saving/loading collection.
 */

public class FileManager {

    private Gson gson = new Gson();

    private GsonBuilder gsonBuilder = new GsonBuilder();

    private String envVariable;

    public FileManager(String envVariable) {this.envVariable = envVariable;}

    /**
     * Writes collection to a file.
     * @param productList Collection to write.
     */

    public void writeCollection(HashMap<Integer, Product> productList) {


        if (System.getenv().get(envVariable) != null) {
            try (FileWriter collectionFileWriter = new FileWriter(new File(System.getenv().get(envVariable)))) {
                JSONArray arrayProductsJson = new JSONArray();
                for (Product product : productList.values()) {
                    JSONObject productJSON = new JSONObject();
                    productJSON.put("id", product.getId());
                    productJSON.put("name", product.getName());
                    JSONObject coordinatesJson = new JSONObject();
                    coordinatesJson.put("x", product.getCoordinates().getX());
                    coordinatesJson.put("y", product.getCoordinates().getY());
                    productJSON.put("coordinates", coordinatesJson);

                    productJSON.put("CreationDate", product.getCreationDate().toString());
                    productJSON.put("price", product.getPrice());
                    productJSON.put("UnitOfMeasure", product.getUnitOfMeasure());
                    JSONObject organizationJSON = new JSONObject();
                    organizationJSON.put("corpId", product.getManufacturer().getId());
                    organizationJSON.put("corpName", product.getManufacturer().getName());
                    organizationJSON.put("fullname", product.getManufacturer().getFullName());
                    organizationJSON.put("OrgType", product.getManufacturer().getType());
                    organizationJSON.put("Adress", product.getManufacturer().getPostalAddress().getStreet());
                    productJSON.put("Organization", organizationJSON);
                    arrayProductsJson.put(productJSON);
                }

                collectionFileWriter.write(arrayProductsJson.toString());


                System.out.println("Коллекция успешна сохранена в файл!");
            } catch (IOException exception) {
                System.out.println("Загрузочный файл является директорией/не может быть открыт!");
            }
        } else System.out.println("Системная переменная с загрузочным файлом не найдена!");
    }

    /**
     * Reads collection from a file.
     * @return Read collection.
     */

    public HashMap<Integer,Product> readCollection() {
        if (System.getenv().get(envVariable) != null) {
            try (FileInputStream collectionFile = new FileInputStream(System.getenv().get(envVariable));
                 BufferedInputStream bis = new BufferedInputStream(collectionFile)) {

                ByteArrayOutputStream buf = new ByteArrayOutputStream();
                int result = bis.read();
                while(result != -1) {
                    buf.write((byte) result);
                    result = bis.read();
                }
                String jsonString = buf.toString("UTF-8");
                JSONArray jsonArray = new JSONArray(jsonString);
                HashMap<Integer,Product> collection = new HashMap<>();

                ArrayList<Integer> productIDList = new ArrayList<>();
                ArrayList<Long> corpIdList = new ArrayList<>();
                Random rand = new Random();
                for (int i = 0; i <jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    while (corpIdList.contains(id)){
                        id = Math.abs( rand.nextInt());
                    }
                    productIDList.add(id);

                    String name = jsonObject.getString("name");
                    JSONObject coordinates = jsonObject.getJSONObject("coordinates");
                    Coordinates coordinatesObj = new Coordinates(coordinates.getLong("x"),coordinates.getDouble("y"));
                    if (coordinatesObj.getX() > 504 || coordinatesObj.getY() <-659){
                        Exception WrongAmountOfElementsException = null;
                        throw WrongAmountOfElementsException;
                    }
                    LocalDate localDate = LocalDate.parse(jsonObject.getString("CreationDate"));
                    double price = jsonObject.getDouble("price");
                    UnitOfMeasure unitOfMeasure = UnitOfMeasure.valueOf(jsonObject.getString("UnitOfMeasure"));
                    if (price <0){
                        throw (Exception) null;
                    }
                    JSONObject organization = jsonObject.getJSONObject("Organization");
                    Long corpId = organization.getLong("corpId");
                    while (corpIdList.contains(corpId)){
                        corpId = Math.abs( rand.nextLong());
                    }
                    corpIdList.add(corpId);
                    String nameOrg = organization.getString("corpName");
                    String fullName =  organization.getString("fullname");
                    Address address = new Address();
                    address.setStreet(organization.getString("Adress"));
                    OrganizationType type = OrganizationType.valueOf(organization.getString("OrgType"));

                    Product product = new Product(id,name, coordinatesObj, price, localDate, unitOfMeasure, new Organization(corpId,nameOrg,fullName,type,address));
                    collection.put(product.getId(),product);
                    if (id <0 || corpId<0){
                        throw (Exception) null;
                    }
                }
                System.out.println(collection);


//                jsonReader.beginArray();
//
//                while (jsonReader.hasNext()) {
//                    jsonReader.beginObject();
//                    StudyGroup studyGroup = gson.fromJson(jsonReader, StudyGroup.class);
//                    collection.add(studyGroup);
//                    jsonReader.endObject();
//                }
//                jsonReader.endArray();
//                System.out.println(jsonReader);

                System.out.println("Коллекция успешна загружена!");
                return collection;
            } catch (FileNotFoundException exception) {
                System.out.println("Загрузочный файл не найден!");
            } catch (NoSuchElementException exception) {
                System.out.println("Загрузочный файл пуст!");
            }catch (JSONException exception){
                System.out.println("Загрузочный файл поврежден!");
            } catch (JsonParseException | NullPointerException exception) {
                System.out.println("В загрузочном файле не обнаружена необходимая коллекция!");
            } catch (IllegalStateException exception) {
                System.out.println("Непредвиденная ошибка!");
                System.exit(0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (WrongAmountOfElementsException e) {
                System.out.println("В загрузочном файле неправильные значения полей!");
            } catch (Exception e) {
                System.out.println("В загрузочном файле неправильные значения!");
            }

        } else System.out.println("Системная переменная с загрузочным файлом не найдена!");
        return new HashMap<>();
         }

    @Override
    public String toString() {
        String string = "FileManager (класс для работы с загрузочным файлом)";
        return string;
    }
}




