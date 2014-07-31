package at.yawk.buycraft;

/**
 * @author yawkat
 */
public class Category {
    private final int id;
    private final String name;
    private final String shortDescription;
    private final int guiItemId;
    private final String itemId;

    Category(int id, String name, String shortDescription, int guiItemId, String itemId) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.guiItemId = guiItemId;
        this.itemId = itemId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortDescription() {
        return shortDescription;
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

        Category category = (Category) o;

        if (guiItemId != category.guiItemId) {
            return false;
        }
        if (id != category.id) {
            return false;
        }
        if (!itemId.equals(category.itemId)) {
            return false;
        }
        if (!name.equals(category.name)) {
            return false;
        }
        if (!shortDescription.equals(category.shortDescription)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + shortDescription.hashCode();
        result = 31 * result + guiItemId;
        result = 31 * result + itemId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Category{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", shortDescription='" + shortDescription + '\'' +
               ", guiItemId=" + guiItemId +
               ", itemId='" + itemId + '\'' +
               '}';
    }
}
