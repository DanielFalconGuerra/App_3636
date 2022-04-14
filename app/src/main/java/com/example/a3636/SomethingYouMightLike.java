package com.example.a3636;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3636.restaurantdata.AddRestaurantInformationSomethingYouLike;

public class SomethingYouMightLike extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_something_you_might_like);

        LinearLayout dataBusinessLayout = (LinearLayout) findViewById(R.id.dataBusinessLayout);
        LinearLayout layoutFunction = new LinearLayout(this);

        AddRestaurantInformationSomethingYouLike Negrito_Cafe = new AddRestaurantInformationSomethingYouLike();
        AddRestaurantInformationSomethingYouLike Curandero = new AddRestaurantInformationSomethingYouLike();
        AddRestaurantInformationSomethingYouLike LaMarinba = new AddRestaurantInformationSomethingYouLike();
        AddRestaurantInformationSomethingYouLike Mirrey = new AddRestaurantInformationSomethingYouLike();
        AddRestaurantInformationSomethingYouLike HappyBox = new AddRestaurantInformationSomethingYouLike();
        AddRestaurantInformationSomethingYouLike LaEscotilla = new AddRestaurantInformationSomethingYouLike();
        AddRestaurantInformationSomethingYouLike Monchster = new AddRestaurantInformationSomethingYouLike();
        AddRestaurantInformationSomethingYouLike ElGranChamorro = new AddRestaurantInformationSomethingYouLike();
        AddRestaurantInformationSomethingYouLike LaCuevaDelLobo = new AddRestaurantInformationSomethingYouLike();
        AddRestaurantInformationSomethingYouLike Indigo = new AddRestaurantInformationSomethingYouLike();
        AddRestaurantInformationSomethingYouLike Manhattan = new AddRestaurantInformationSomethingYouLike();
        AddRestaurantInformationSomethingYouLike PosdataCoffeAndBakery = new AddRestaurantInformationSomethingYouLike();
        AddRestaurantInformationSomethingYouLike BuenaVida = new AddRestaurantInformationSomethingYouLike();
        AddRestaurantInformationSomethingYouLike Arena88 = new AddRestaurantInformationSomethingYouLike();
        AddRestaurantInformationSomethingYouLike Raices = new AddRestaurantInformationSomethingYouLike();

        Negrito_Cafe.setBusinessName("Negrito Café");
        Negrito_Cafe.setBusinessDescription("Ven y disfruta de una buena taza de café");
        Negrito_Cafe.setBusinessLogo(R.mipmap.negritocafe);

        Curandero.setBusinessName("Curandero");
        Curandero.setBusinessDescription("Ven y disfruta de las mejores tortas, ahogadas, guacamayas, tacos ahogados, " +
                "alitas & más. Así como más deliciosos productos que manejamos");
        Curandero.setBusinessLogo(R.mipmap.elcurandero);

        LaMarinba.setBusinessName("La Marinba");
        LaMarinba.setBusinessDescription("Bebida = Comida");
        LaMarinba.setBusinessLogo(R.mipmap.marinba);

        Mirrey.setBusinessName("Mirrey");
        Mirrey.setBusinessDescription("Ven y disfruta del mejor lugar de Irapuato...Alcohol, Diversión y mucho más");
        Mirrey.setBusinessLogo(R.mipmap.mirrey);

        HappyBox.setBusinessName("Happy Box");
        HappyBox.setBusinessDescription("Negocio");
        HappyBox.setBusinessLogo(R.mipmap.happybox);

        LaEscotilla.setBusinessName("La Escotilla");
        LaEscotilla.setBusinessDescription("Restaurante, barra fría de mariscos, ceviches, tostadas, aguachiles, tiraditos...");
        LaEscotilla.setBusinessLogo(R.mipmap.laescotilla);

        Monchster.setBusinessName("Monchster");
        Monchster.setBusinessDescription("Monchster es un espacio donde nos guiamos por los sabores y el antojo para ofrecer " +
                "comida con actitud, lo cual se ve reflejada en nuestra carta que ofrece a nuestros invitados desde Pastas y Ensaladas hasta " +
                "Calzone, Pay Casero y Tablas de Charcutería");
        Monchster.setBusinessLogo(R.mipmap.monchster);

        ElGranChamorro.setBusinessName("granchamorro");
        ElGranChamorro.setBusinessDescription("Delicioso chamorro adobado envuelto en hoja de plátano, contamos con " +
                "servicio a domicilio por Handybody y llevamos a tus reuniones o grandes eventos");
        ElGranChamorro.setBusinessLogo(R.mipmap.happybox);

        LaCuevaDelLobo.setBusinessName("La cueva del lobo");
        LaCuevaDelLobo.setBusinessDescription("Donde encontrarás una gran variedad de comida...Desde unas hamburguesas buenísimas " +
                "hasta unos molcajetes acompañados con una michelada. Además de los mejores mezcales de la casa");
        LaCuevaDelLobo.setBusinessLogo(R.mipmap.cuevalobo);

        Indigo.setBusinessName("Indigo");
        Indigo.setBusinessDescription("De noche, el cielo se vuelve índigo");
        Indigo.setBusinessLogo(R.mipmap.indigobar);

        Manhattan.setBusinessName("Manhattan");
        Manhattan.setBusinessDescription("Restaurante norteamericano, Bar & Grill");
        Manhattan.setBusinessLogo(R.mipmap.manhattanbar);

        HappyBox.setBusinessName("Happy Box");
        HappyBox.setBusinessDescription("Negocio");
        HappyBox.setBusinessLogo(R.mipmap.happybox);

        PosdataCoffeAndBakery.setBusinessName("Posdata Coffe&Bakery");
        PosdataCoffeAndBakery.setBusinessDescription("Lugar de postres y café");
        PosdataCoffeAndBakery.setBusinessLogo(R.mipmap.posdata);

        BuenaVida.setBusinessName("Buena Vida");
        BuenaVida.setBusinessDescription("Somos un pequeño espacio para disfrutar en compañía de una buena cerveza y tacos, " +
                "todo hecho con mucho amor por México");
        BuenaVida.setBusinessLogo(R.mipmap.buenavida);

        Arena88.setBusinessName("Arena 88");
        Arena88.setBusinessDescription("Arena 88 es un concepto innovador de Sport-Bar con una gastronomía con especialidad en Mariscos, " +
                "sin dejar a un lado los cortes y snacks, ofrecemos ser un punto de reunión para divertirse y pasar un rato agradable.");
        Arena88.setBusinessLogo(R.mipmap.arena88);

        Raices.setBusinessName("Raíces");
        Raices.setBusinessDescription("Somos una antojería mexicana que te conecta con las raíces de los sabores originales donde " +
                "podrás acompañar tus alimentos con un buen trago a base de mezcal o cheves con un ambiente agradable para toda la familia.");
        Raices.setBusinessLogo(R.mipmap.raices);


        for (int i = 0; i < 15; i++){
            switch (i){
                case 0:
                    layoutFunction = addInformationBussines(Negrito_Cafe.getBusinessName(), Negrito_Cafe.getBusinessDescription(), Negrito_Cafe.getBusinessLogo());
                    dataBusinessLayout.addView(layoutFunction);
                    break;
                case 1:
                    layoutFunction = addInformationBussines(Curandero.getBusinessName(), Curandero.getBusinessDescription(), Curandero.getBusinessLogo());
                    dataBusinessLayout.addView(layoutFunction);
                    break;
                case 2:
                    layoutFunction = addInformationBussines(LaMarinba.getBusinessName(), LaMarinba.getBusinessDescription(), LaMarinba.getBusinessLogo());
                    dataBusinessLayout.addView(layoutFunction);
                    break;
                case 3:
                    layoutFunction = addInformationBussines(Mirrey.getBusinessName(), Mirrey.getBusinessDescription(), Mirrey.getBusinessLogo());
                    dataBusinessLayout.addView(layoutFunction);
                    break;
                case 4:
                    layoutFunction = addInformationBussines(HappyBox.getBusinessName(), HappyBox.getBusinessDescription(), HappyBox.getBusinessLogo());
                    dataBusinessLayout.addView(layoutFunction);
                    break;
                case 5:
                    layoutFunction = addInformationBussines(LaEscotilla.getBusinessName(), LaEscotilla.getBusinessDescription(), LaEscotilla.getBusinessLogo());
                    dataBusinessLayout.addView(layoutFunction);
                    break;
                case 6:
                    layoutFunction = addInformationBussines(Monchster.getBusinessName(), Monchster.getBusinessDescription(), Monchster.getBusinessLogo());
                    dataBusinessLayout.addView(layoutFunction);
                    break;
                case 7:
                    layoutFunction = addInformationBussines(ElGranChamorro.getBusinessName(), ElGranChamorro.getBusinessDescription(), ElGranChamorro.getBusinessLogo());
                    dataBusinessLayout.addView(layoutFunction);
                    break;
                case 8:
                    layoutFunction = addInformationBussines(LaCuevaDelLobo.getBusinessName(), LaCuevaDelLobo.getBusinessDescription(), LaCuevaDelLobo.getBusinessLogo());
                    dataBusinessLayout.addView(layoutFunction);
                    break;
                case 9:
                    layoutFunction = addInformationBussines(Indigo.getBusinessName(), Indigo.getBusinessDescription(), Indigo.getBusinessLogo());
                    dataBusinessLayout.addView(layoutFunction);
                    break;
                case 10:
                    layoutFunction = addInformationBussines(Manhattan.getBusinessName(), Manhattan.getBusinessDescription(), Manhattan.getBusinessLogo());
                    dataBusinessLayout.addView(layoutFunction);
                    break;
                case 11:
                    layoutFunction = addInformationBussines(PosdataCoffeAndBakery.getBusinessName(), PosdataCoffeAndBakery.getBusinessDescription(), PosdataCoffeAndBakery.getBusinessLogo());
                    dataBusinessLayout.addView(layoutFunction);
                    break;
                case 12:
                    layoutFunction = addInformationBussines(BuenaVida.getBusinessName(), BuenaVida.getBusinessDescription(), BuenaVida.getBusinessLogo());
                    dataBusinessLayout.addView(layoutFunction);
                    break;
                case 13:
                    layoutFunction = addInformationBussines(Arena88.getBusinessName(), Arena88.getBusinessDescription(), Arena88.getBusinessLogo());
                    dataBusinessLayout.addView(layoutFunction);
                    break;
                case 14:
                    layoutFunction = addInformationBussines(Raices.getBusinessName(), Raices.getBusinessDescription(), Raices.getBusinessLogo());
                    dataBusinessLayout.addView(layoutFunction);
                    break;
            }
        }
    }
    public LinearLayout addInformationBussines(String name, String description, int logo){
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView nameBusiness = new TextView(this);
        TextView descriptionBusiness = new TextView(this);
        ImageView logoBusiness = new ImageView(this);

        nameBusiness.setGravity(Gravity.CENTER);
        descriptionBusiness.setGravity(Gravity.CENTER);

        nameBusiness.setTextColor(Color.BLACK);
        descriptionBusiness.setTextColor(Color.BLACK);

        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(300, 300);
        layoutParams.gravity=Gravity.CENTER;
        logoBusiness.setLayoutParams(layoutParams);
        //logoBusiness.setLayoutParams(new LinearLayout.LayoutParams(300,300));
        logoBusiness.setImageResource(logo);
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) logoBusiness.getLayoutParams();
        marginParams.setMargins(30, 30, 30, 30);

        nameBusiness.setText(name);
        descriptionBusiness.setText(description);

        layout.addView(logoBusiness);
        layout.addView(nameBusiness);
        layout.addView(descriptionBusiness);

        return layout;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(this,"Inicio", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item2:
                Toast.makeText(this,"Contacto", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item3:
                Toast.makeText(this,"Login", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}