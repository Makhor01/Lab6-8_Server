package utility;

import data.Product;
import data.UnitOfMeasure;
import exceptions.CollectionIsEmptyException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Operates the collection itself.
 */

public class CollectionManager {

    private HashMap<Integer, Product> productCollection = new HashMap<>();

    private LocalDateTime lastInitTime;

    private LocalDateTime lastSaveTime;

    private FileManager fileManager;
    public CollectionManager(FileManager fileManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.fileManager = fileManager;

        loadCollection();
    }

    public Integer generateProductId(){
       ArrayList<Integer> list = new ArrayList<>(productCollection.keySet());
        Random rand = new Random();
        Integer x = Math.abs( rand.nextInt());
       while (list.contains(x)){
           x = Math.abs( rand.nextInt());
       }
        return x;
    }
    public Long generateOrgId(){

        ArrayList<Long> targetList = new ArrayList<>();
        for (Map.Entry<Integer, Product> entry : productCollection.entrySet()) {
            if (entry.getKey() > 1) { // Фильтруем элементы по условию
                targetList.add(entry.getValue().getManufacturer().getId());
            }
        }
        Random rand = new Random();
        Long x = Math.abs( rand.nextLong());
        while (targetList.contains(x)){
            x = Math.abs( rand.nextLong());
        }
        return x;
    }

    /**
     * @return The collection itself.
     */

    public HashMap<Integer,Product> getCollection() {return productCollection;}

    /**
     * @return Last initialization time or null if there wasn't initialization.
     */

    public LocalDateTime getLastInitTime() {return lastInitTime;}

    /**
     * @return Last save time or null if there wasn't saving.
     */

    public LocalDateTime getLastSaveTime() {return lastSaveTime;}

    /**
     * @return Name of the collection's type.
     */

    public String collectionType() {return productCollection.getClass().getName();}

    /**
     * @return Size of the collection.
     */

    public int collectionSize() {return  productCollection.size();}

    /**
     * @return The first element of the collection or null if collection is empty.
     */

    public Product getFirst() {
        if (productCollection.isEmpty()) return null;
        return productCollection.get(1);
    }



    /**
     * @param id ID of the group.
     * @return A group by his ID or null if group isn't found.
     */

    public Product getById(int id) {
        for (Product product: productCollection.values()) {
            if (product.getId()==id) return product;
        }
        return null;
    }
    public String countGreaterThan(String argument){
        UnitOfMeasure unit = UnitOfMeasure.valueOf(argument);
        int summ = 0;
        for (Product product:  productCollection.values()){
            if (unit.getValue()<product.getUnitOfMeasure().getValue()){
                summ+=1;
            }
        }
        return String.valueOf(summ);
    }


    /**
     * @param groupToFind A marine who's value will be found.
     * @return A group by his value or null if marine isn't found.
     */

    /*public ProductAsk getByValue(Product groupToFind) {
        for (ProductAsk group : productCollection) {
            if (group.equals(groupToFind)) return group;
        }
        return null;
    }*/

    /**
     * @return Sum of all transferred students or 0 if collection is empty.
     */

    public double getSumOfPrice() {
        double sumPrice = 0;
        for (Product product: productCollection.values()) {
            sumPrice += product.getPrice();
        }
        return sumPrice;
    }

    /**
     * @return Group, whose semesterEnum field value is the minimum.
     * @throws CollectionIsEmptyException If collection is empty.
     */

    public Product minById() throws CollectionIsEmptyException {
        if (productCollection.isEmpty()) throw new CollectionIsEmptyException();

        Product minGroup = productCollection.entrySet().iterator().next().getValue();
        for (Product group: productCollection.values()) {
            if(group.getPrice() <minGroup.getPrice()) {
                minGroup = group;
            }
        }
        return minGroup;
    }






    public void addToCollection(Product product) {
//        group.setId(IDprovider.getInstance().getID());
        product.setId(generateProductId());
        product.setManufacturerID(generateOrgId());
        productCollection.put(product.getId(),product);
    }

    /**
     * Removes a group from collection.
     * @param group A group to remove.
     */


    public void removeFromCollection(Product group) throws CollectionIsEmptyException {
        if (productCollection.isEmpty()) throw new CollectionIsEmptyException();
        productCollection.remove(group.getId());
    }


    /**
     * Clears the collection.
     */

    public void clearCollection() {
        productCollection.clear();
    }

    /**
     * Saves the collection to file.
     */

    public void saveCollection() {
        fileManager.writeCollection(productCollection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Loads the collection from file.
     */

    public void loadCollection() {
        productCollection = fileManager.readCollection();
        lastInitTime = LocalDateTime.now();
    }
}
