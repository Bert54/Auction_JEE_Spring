package data;

import java.lang.reflect.Array;

public class Categories {

    private static final Categories instance = new Categories();

    private final String[] categories;

    private Categories() {
        this.categories = new String[]{
                "Collectibles",
                "Coins & paper money",
                "Antiques",
                "Sports memorabilia",
                "Computers & tablets",
                "Cameras & photo",
                "TV & audio & surveillance",
                "Cell phones & accessories",
                "Video games & consoles",
                "Music",
                "DVDs & movies",
                "Tickets & experiences",
                "Women fashion",
                "Men fashion",
                "Watches",
                "Sneakers",
                "Yard & garden & outdoor",
                "Crafts",
                "Home improvement",
                "Pet supplies",
                "Vehicle parts & accessories",
                "Cars & trucks",
                "Motorcycles",
                "Other vehicles",
                "Outdoor sports",
                "Team sports",
                "Exercise & fitness",
                "Golf",
                "Trading cards",
                "Kids toys",
                "Action figures",
                "Dolls & bears",
                "Git Cards",
                "Health & beauty",
                "Musical instruments & gear",
                "Business & industrial",
        };
    }

    public static Categories getInstance() {
        return instance;
    }

    public String[] getCategories() {
        return this.categories;
    }

}
