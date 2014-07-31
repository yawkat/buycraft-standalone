package at.yawk.buycraft;

/**
 * @author yawkat
 */
public class Package {
    private final int id;
    private final int order;
    private final String name;
    private final String description;
    private final String shortDescription;
    private final String price;
    private final int categoryId;
    private final int guiItemId;
    private final String itemId;

    Package(int id, int order, String name, String description, String shortDescription, String price,
                   int categoryId, int guiItemId, String itemId) {
        this.id = id;
        this.order = order;
        this.name = name;
        this.description = description;
        this.shortDescription = shortDescription;
        this.price = price;
        this.categoryId = categoryId;
        this.guiItemId = guiItemId;
        this.itemId = itemId;
    }

    public int getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getPrice() {
        return price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getGuiItemId() {
        return guiItemId;
    }

    public String getItemId() {
        return itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Package aPackage = (Package) o;

        if (categoryId != aPackage.categoryId) {
            return false;
        }
        if (guiItemId != aPackage.guiItemId) {
            return false;
        }
        if (id != aPackage.id) {
            return false;
        }
        if (!itemId.equals(aPackage.itemId)) {
            return false;
        }
        if (order != aPackage.order) {
            return false;
        }
        if (!description.equals(aPackage.description)) {
            return false;
        }
        if (!name.equals(aPackage.name)) {
            return false;
        }
        if (!price.equals(aPackage.price)) {
            return false;
        }
        if (!shortDescription.equals(aPackage.shortDescription)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + order;
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + shortDescription.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + categoryId;
        result = 31 * result + guiItemId;
        result = 31 * result + itemId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Package{" +
               "id=" + id +
               ", order=" + order +
               ", name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", shortDescription='" + shortDescription + '\'' +
               ", price='" + price + '\'' +
               ", categoryId=" + categoryId +
               ", guiItemId=" + guiItemId +
               ", itemId='" + itemId + '\'' +
               '}';
    }
}
