package com.example.android.bakingapp.data.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
@Entity
public class RecipeRoom {


        @PrimaryKey
        private Integer id;

        private String name;

        private Integer servings;

        private String image;


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getServings() {
            return servings;
        }

        public void setServings(Integer servings) {
            this.servings = servings;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }





}
