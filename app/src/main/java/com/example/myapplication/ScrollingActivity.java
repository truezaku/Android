package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class ScrollingActivity extends AppCompatActivity {

    private SearchView searchView;
    public static boolean purchasedId[];
    //public static boolean purchasedIdZtoA[];

    public class ShopData {
        public String name;
        public int imageResource;
        public String description;
        public String keyword;
        public int cost;
        public boolean purchased;
        public String date;

        public ShopData(String nm, int res, String desc, String kw, int cst, boolean pur, String dt) {
            name = nm;
            imageResource = res;
            description = desc;
            keyword = kw;
            cost = cst;
            purchased = pur;
            date = dt;
        }
    }

    ShopData data[] = {
            new ShopData("Bone", R.drawable.images, "Good chew toy.","toy", 1,false, "2020/09/24"),
            new ShopData("Carrot", R.drawable.carrot, "Good chew.", "food",1, false, "2020/09/22"),
            new ShopData("Dog", R.drawable.dog, "Chews toy.", "toy", 2, false, "2020/09/26"),
            new ShopData("Flame", R.drawable.flame, "It burns.", "fire",1, false, "2020/09/23"),
            new ShopData("Grapes", R.drawable.grapes, "Your eat them.", "food",1, false, "2020/09/26"),
            new ShopData("House", R.drawable.house, "As opposed to home.", "room",100, false, "2020/09/24"),
            new ShopData("Lamp", R.drawable.lamp, "It lights.", "light",2, false, "2020/09/26"),
            new ShopData("Mouse", R.drawable.mouse, "Not a rat.", "toy",1, false, "2020/09/21"),
            new ShopData("Nail", R.drawable.nail, "Hammer required.", "toy",1, false, "2020/09/25"),
            new ShopData("Penguin", R.drawable.penguin, "Find Batman.", "toy",10, false, "2020/09/25"),
            new ShopData("Rocks", R.drawable.rocks, "Rolls.", "toy",1, false, "2020/09/26"),
            new ShopData("Star", R.drawable.star, "Like the sun but farther away.", "toy",25, false, "2020/09/20"),
            new ShopData("Toad", R.drawable.toad, "Like a frog.", "toy",1, false, "2020/09/21"),
            new ShopData("Van", R.drawable.van, "Has four wheels.", "toy",10, false, "2020/09/26"),
            new ShopData("Wheat", R.drawable.wheat, "Some breads have it.", "toy",1, false, "2020/09/26"),
            new ShopData("Yak", R.drawable.yak, "Yakity Yak Yak.", "toy",15, false, "2020/09/23"),
    };

    class CustomDialogClass extends Dialog {

        public int m_id;
        public LinearLayout m_view;
        public int selected_id;
        public int flag;

        public CustomDialogClass(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.shop_dialog);
            purchasedId = new boolean[16];
            Arrays.fill(purchasedId, false);
            /*
            purchasedIdZtoA = new boolean[16];
            Arrays.fill(purchasedIdZtoA, false);

             */

            Button yes = this.findViewById(R.id.purchaseButton);
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < m_view.getChildCount(); ++i) {
                        View v = m_view.getChildAt(i);
                        TextView selectNameText = v.findViewById(R.id.nameButton);
                        String nameString = selectNameText.getText().toString();
                        if (data[m_id].name == nameString) {
                            selected_id = i;
                            purchasedId[m_id] = true;
                            data[m_id].purchased = true;
                            break;
                        }
                    }
                    /*
                    for (int i = 0; i < purchasedId.length; ++i) {
                        purchasedIdZtoA[purchasedId.length - 1 - i] = purchasedId[i];
                    }

                     */

                    int price = data[m_id].cost;
                    flag = MinusBalance(price);
                    // If the balance hasn't enough money to buy it, you can't buy.
                    if ( flag == 1 ) {
                        dismiss();
                    }
                    else {
                        m_view.removeViewAt(selected_id);
                        dismiss();
                    }
                }
            });

            Button no = this.findViewById(R.id.cancelButton);
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }

        public void SetDetails(int id) {
            TextView nameText = this.findViewById(R.id.nameText);
            nameText.setText(data[id].name);

            TextView costText = this.findViewById(R.id.costText);
            costText.setText("$" + data[id].cost);

            TextView descText = this.findViewById(R.id.descText);
            descText.setText(data[id].description);

            ImageView imageView = this.findViewById(R.id.imageView);
            imageView.setImageResource(data[id].imageResource);

            m_id = id;
        }
    }

    public CustomDialogClass customDialog = null;

    public void ShopClicked(int id) {
        customDialog.show();
        customDialog.SetDetails(id);
    }

    private void InitShopItems() {
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout layout = findViewById(R.id.layoutScrollView);
        customDialog.m_view = layout;

        for (int i = 0; i < data.length; ++i) {
            View myShopItems = inflater.inflate(R.layout.shop_item, null);
            final int tmp_id = i;

            View.OnClickListener click = new View.OnClickListener() {
                public int id = tmp_id;

                @Override
                public void onClick(View view) {
                    ShopClicked(id);
                }
            };

            Button nameButton = myShopItems.findViewById(R.id.nameButton);
            nameButton.setText(data[i].name);
            nameButton.setOnClickListener(click);

            Button costButton = myShopItems.findViewById(R.id.costButton);
            costButton.setText("$" + data[i].cost);
            costButton.setOnClickListener(click);

            ImageButton imageButton = myShopItems.findViewById(R.id.imageButton);
            imageButton.setImageResource(data[i].imageResource);
            imageButton.setOnClickListener(click);

            layout.addView(myShopItems);
        }
    }

    public void InitShopItems2() {
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout layout = findViewById(R.id.layoutScrollView);
        layout.removeAllViews();
        customDialog.m_view = layout;

        for (int i = 0; i < data.length; ++i) {
            if (data[i].purchased == false) {
                View myShopItems = inflater.inflate(R.layout.shop_item, null);
                final int tmp_id = i;

                View.OnClickListener click = new View.OnClickListener() {
                    public int id = tmp_id;

                    @Override
                    public void onClick(View view) {
                        ShopClicked(id);
                    }
                };

                Button nameButton = myShopItems.findViewById(R.id.nameButton);
                nameButton.setText(data[i].name);
                nameButton.setOnClickListener(click);

                Button costButton = myShopItems.findViewById(R.id.costButton);
                costButton.setText("$" + data[i].cost);
                costButton.setOnClickListener(click);

                ImageButton imageButton = myShopItems.findViewById(R.id.imageButton);
                imageButton.setImageResource(data[i].imageResource);
                imageButton.setOnClickListener(click);

                layout.addView(myShopItems);
            }
        }
    }

    public void SortByNameAtoZ() {
        Arrays.sort(data, new Comparator<ShopData>() {
            public int compare(ShopData s1, ShopData s2) {
                return s1.name.compareToIgnoreCase(s2.name);
            }
        });
        InitShopItems2();
    }

    public void SortByNameZtoA() {
        Arrays.sort(data, new Comparator<ShopData>() {
            public int compare(ShopData s1, ShopData s2) {
                return s2.name.compareToIgnoreCase(s1.name);
            }
        });
        /*
        if(purchasedIdZtoA == null && purchasedId == null) {
            purchasedIdZtoA = new boolean[16];
            Arrays.fill(purchasedIdZtoA, false);
        }
        else if(purchasedIdZtoA == null && purchasedId != null) {
            for (int i = 0; i < purchasedId.length; ++i) {
                purchasedIdZtoA[purchasedId.length - 1 - i] = purchasedId[i];
            }
        }
         */
        InitShopItems2();
    }

    public int sortcnt_cst = 0;
    public void SortByCost() {
        if (sortcnt_cst%2 == 0) {
            Arrays.sort(data, new Comparator<ShopData>() {
                public int compare(ShopData s1, ShopData s2) {
                    return Integer.valueOf(s1.cost).compareTo(Integer.valueOf(s2.cost));
                }
            });
        }
        else {
            Arrays.sort(data, new Comparator<ShopData>() {
                public int compare(ShopData s1, ShopData s2) {
                    return Integer.valueOf(s2.cost).compareTo(Integer.valueOf(s1.cost));
                }
            });
        }
        InitShopItems2();
    }

    public int sortcnt_dt = 0;
    public void SortByDate() {
        if (sortcnt_dt%2 == 0) {
            Arrays.sort(data, new Comparator<ShopData>() {
                public int compare(ShopData s1, ShopData s2) {
                    return s1.date.compareToIgnoreCase(s2.date);
                }
            });
        }
        else {
            Arrays.sort(data, new Comparator<ShopData>() {
                public int compare(ShopData s1, ShopData s2) {
                    return s2.date.compareToIgnoreCase(s1.date);
                }
            });
        }
        InitShopItems2();
    }

    public void FabClicked() {
        TextView moneyText = findViewById(R.id.moneyText);
        String moneyString = moneyText.getText().toString();
        int money = Integer.parseInt(moneyString);
        money += 10;
        moneyText.setText("" + money);
    }

    public int MinusBalance(int price) {
        int flag = 0;
        TextView moneyText = findViewById(R.id.moneyText);
        String moneyString = moneyText.getText().toString();
        TextView message = findViewById(R.id.messageText);
        int balance = Integer.parseInt(moneyString);

        if (balance < price){
            flag = 1;
            message.setText("Not enough money to buy it.");
        }
        else {
            balance -= price;
            message.setText("");
        }
        moneyText.setText("" + balance);
        return flag;
    }

    public void Logout() {
        Button loginButton = findViewById(R.id.userLoginButton);
        loginButton.setText("LOGIN");

        username = null;

        TextView yourId = findViewById(R.id.yourId);
        yourId.setText("Hello Guest");

        TextView moneyText = findViewById(R.id.moneyText);
        moneyText.setText("100");

        TextView message = findViewById(R.id.messageText);
        message.setText("");
        // Need to add a function that can refresh the cart and inventory here.
        //
        //
        final LinearLayout layout = findViewById(R.id.layoutScrollView);
        layout.removeAllViews();
        purchasedId = new boolean[16];
        InitShopItems();

    }
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar2); // Why it has 2? but if it's missing, the tool bar section will disappear
        setSupportActionBar(toolbar2);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        // this.searchView = menu
        if (getIntent().hasExtra("USER_NAME")){
            username = getIntent().getStringExtra("USER_NAME");
            TextView yourId = findViewById(R.id.yourId);
            yourId.setText("Hello " + username);
        }

        // Idk why but fab button disappears
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                 */
                FabClicked();
            }
        });

        /*
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FabClicked();
            }
        });

         */


        EditText searchText = (EditText) findViewById(R.id.editText);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                final LinearLayout layout = findViewById(R.id.layoutScrollView);
                LayoutInflater inflater = LayoutInflater.from(ScrollingActivity.this);

                layout.removeAllViews();
                String text = s.toString();
                //filter(s.toString());
                if (text.isEmpty() || text.trim().equals("") || text.length() < 3){
                    String foo = text; // do nothing
                }
                //if (text != null || text != " ")
                else {
                    for (int i = 0; i < data.length; ++i) {
                        if ((data[i].name.toLowerCase().contains(text.toLowerCase()) || data[i].description.toLowerCase().contains(text.toLowerCase()) || data[i].keyword.toLowerCase().contains(text.toLowerCase())) && data[i].purchased == false) {
                            View myShopItems = inflater.inflate(R.layout.shop_item, null);
                            final int tmp_id = i;

                            View.OnClickListener click = new View.OnClickListener() {
                                public int id = tmp_id;

                                @Override
                                public void onClick(View view) {
                                    ShopClicked(id);
                                }
                            };

                            Button nameButton = myShopItems.findViewById(R.id.nameButton);
                            nameButton.setText(data[i].name);
                            nameButton.setOnClickListener(click);

                            Button costButton = myShopItems.findViewById(R.id.costButton);
                            costButton.setText("$" + data[i].cost);
                            costButton.setOnClickListener(click);

                            ImageButton imageButton = myShopItems.findViewById(R.id.imageButton);
                            imageButton.setImageResource(data[i].imageResource);
                            imageButton.setOnClickListener(click);

                            layout.addView(myShopItems);
                        }
                    }
                }
            }
        });


        customDialog = new CustomDialogClass(this);
        InitShopItems();

        final Button loginButton = findViewById(R.id.userLoginButton);
        if (username != null) {
            loginButton.setText("LOGOUT");
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (username != null) {
                    Logout();
                }
                else {
                    Intent intent = new Intent(ScrollingActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);

        /*
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default


         */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_fav:
                return true;
                // FabClicked();

            case R.id.sort_ZtoA:
                SortByNameZtoA();
                return true;

            case R.id.sort_AtoZ:
                SortByNameAtoZ();
                return true;

            case R.id.sort_cost:
                sortcnt_cst++;
                SortByCost();
                return true;

            case R.id.sort_date:
                sortcnt_dt++;
                SortByDate();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}