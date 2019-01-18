package com.sb.blumek.daos;

import com.sb.blumek.models.Category;
import com.sb.blumek.models.Post;
import com.sb.blumek.models.Subcategory;
import com.sb.blumek.models.Thread;
import com.sb.blumek.utils.DBManager;

import java.util.List;

public class CategoryDAO {

    public List<Category> getCategories() {
        return DBManager.getCategories();
    }

    public Category getCategory(Integer categoryId) {
        return DBManager.getCategory(categoryId);
    }

    public void createCategory(Category category) {
        DBManager.createCategory(category);
    }

    public List<Subcategory> getCategorySubcategories(Integer categoryId) {
        return DBManager.getCategorySubcategories(categoryId);
    }

    public Subcategory getSubcategory(Integer subcategoryId) {
        return DBManager.getSubcategory(subcategoryId);
    }

    public Post getLastSubcategoryPost(Integer categoryId, Integer subcategoryId) {
        return DBManager.getLastSubcategoryPost(categoryId, subcategoryId);
    }

    public List<Thread> getSubcategoryThreads(Integer subcategoryId) {
        return DBManager.getSubcategoryThreads(subcategoryId);
    }

    public void createCategorySubcategory(Integer categoryId, Subcategory subcategory) {
        DBManager.createCategorySubcategory(categoryId, subcategory);
    }
}
