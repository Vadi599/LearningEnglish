package com.example.learningenglish.main;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.learningenglish.R;
import com.example.learningenglish.adapter.CategoryAdapter;
import com.example.learningenglish.model.Category;
import com.example.learningenglish.model.ThemeProperty;
import com.example.learningenglish.selection_menu.SelectionMenuFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements MainContract.View {

    List<Category> categories;
    RecyclerView recyclerView;
    CategoryAdapter adapter;
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rvCategories);
        categories = new ArrayList<>();
        categories.add(new Category("Семья и друзья", R.drawable.family_and_friends));
        categories.add(new Category("Части тела", R.drawable.body_parts));
        categories.add(new Category("Одежда", R.drawable.clothes));
        categories.add(new Category("Цифры", R.drawable.digits));
        categories.add(new Category("Цвета", R.drawable.colors));
        categories.add(new Category("Фрукты и овощи", R.drawable.fruits_and_vegetables));
        categories.add(new Category("Еда", R.drawable.food));
        categories.add(new Category("Школа", R.drawable.school));
        categories.add(new Category("Погода и природа", R.drawable.weather));
        categories.add(new Category("Животные", R.drawable.animals));
        adapter = new CategoryAdapter(categories);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        presenter = new MainPresenter(this, getBaseContext());
        presenter.getDataFromDatabase();
        adapter.setOnItemClickListener(this::showSelectionMenu);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showThemeProperties(List<ThemeProperty> properties) {
        adapter.setThemePropertyList(properties);
    }

    @Override
    public void showSelectionMenu(ThemeProperty property) {
        Bundle bundle = new Bundle();
        bundle.putInt("themeId", property.getId());
        SelectionMenuFragment fragment = new SelectionMenuFragment();
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}


