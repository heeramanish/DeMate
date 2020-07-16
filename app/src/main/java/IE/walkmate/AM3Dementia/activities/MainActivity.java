 package IE.walkmate.AM3Dementia.activities;

 import android.content.Intent;
 import android.os.Bundle;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.ImageView;
 import android.widget.LinearLayout;
 import android.widget.TextView;

 import androidx.appcompat.app.AppCompatActivity;
 import androidx.core.content.ContextCompat;
 import androidx.viewpager2.widget.ViewPager2;

 import com.google.android.material.button.MaterialButton;

 import java.util.ArrayList;
 import java.util.List;

 import IE.walkmate.AM3Dementia.R;

 public class MainActivity extends AppCompatActivity {
    private TextView tvSkip_1;
    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicators);
        setupOnboardingItems();

        final ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);

        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);

        tvSkip_1 = findViewById(R.id.tv_skip_1);
        setupOnboardingIndicators();
        setupCurrentOnboradingIndicator(0);



        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setupCurrentOnboradingIndicator(position);
            }
        });

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(onboardingViewPager.getCurrentItem() + 1  < onboardingAdapter.getItemCount()){
                onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem()+1);
            }else {
                startActivity(new Intent(getApplicationContext(), Launch_Activity.class));
                finish();
            }

            }
        });

        //on click listener for skip
        tvSkip_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent skip = new Intent(MainActivity.this,Launch_Activity.class);
                startActivity(new Intent(getApplicationContext(), Launch_Activity.class));

            }
        });
    }

    private void setupOnboardingItems() {
        List<OnboardingItem> OnboardingItems = new ArrayList<>();

     
        OnboardingItem itemApp1 = new OnboardingItem();
        itemApp1.setTitle("DeMate");
        itemApp1.setDescription("A mobile application to reduce dementia by recommending low-intensity exercises aimed at improving memory retention, provided with reminder and mood analyzing features.");
        itemApp1.setImage(R.drawable.brainlogoimage);

     
        OnboardingItem itemDementiaCentres = new OnboardingItem();
        itemDementiaCentres.setTitle("Dementia Centres Near Me");
        itemDementiaCentres.setDescription(" Find Dementia Care Centres to be better prepared to take care of your loved one.");
        itemDementiaCentres.setImage(R.drawable.centre_onboard);

        OnboardingItem itemExerciseReminder = new OnboardingItem();
        itemExerciseReminder.setTitle("Reminders");
        itemExerciseReminder.setDescription(" Dementia patient can set reminders based on their convenience, ensuring that no day goes by without engaging in memory enhancing activities.");
        itemExerciseReminder.setImage(R.drawable.reminder_onboard);


        OnboardingItem itemVideoTutorial = new OnboardingItem();
        itemVideoTutorial.setTitle("Exercise Tutorials");
        itemVideoTutorial.setDescription(" Low intensity workout videos grouped according to category, ensuring easy access to memory enhancing videos.");
        itemVideoTutorial.setImage(R.drawable.yoga_onboard);

        OnboardingItem itemMoodAnalysis = new OnboardingItem();
        itemMoodAnalysis.setTitle("Mood Analyzer");
        itemMoodAnalysis.setDescription("A feature designed to track the patient's mood overtime based on the exercise activity undertaken, to observe long term trends and mood fluctuations. ");
        itemMoodAnalysis.setImage(R.drawable.mood_onboradin);
         
       
        OnboardingItem itemSwipe = new OnboardingItem();
        itemSwipe.setTitle("Swipe Left or Right to see Emotions");
        itemSwipe.setDescription(" Patient can swipe either left or right as per their mood to record a specific emotion.");
        itemSwipe.setImage(R.drawable.swipe_onboard);

        OnboardingItem itemSimply = new OnboardingItem();
        itemSimply.setTitle("Emoticons");
        itemSimply.setDescription("Each smiley represents a different emotion, patient has the liberty to select any of the emotion by a simple tap on the emoticon.");
        itemSimply.setImage(R.drawable.smily_icon_onboard);


        OnboardingItems.add(itemApp1);
        OnboardingItems.add(itemDementiaCentres);
        OnboardingItems.add(itemExerciseReminder);
        OnboardingItems.add(itemVideoTutorial);
        OnboardingItems.add(itemMoodAnalysis);
        OnboardingItems.add(itemSwipe);
        OnboardingItems.add(itemSimply);

        onboardingAdapter = new OnboardingAdapter(OnboardingItems);
    }
    private void setupOnboardingIndicators()
    {

            ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(8,0,8,0);
            for(int i =0;i< indicators.length;i++)
            {
                indicators[i] = new ImageView(getApplicationContext());
                indicators[i].setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.onboarding_indicator_inactive
                ));
                indicators[i].setLayoutParams(layoutParams);
                layoutOnboardingIndicators.addView(indicators[i]);

            }

        }


        private void setupCurrentOnboradingIndicator(int index){

        int childCount = layoutOnboardingIndicators.getChildCount();
        for(int i =0; i<childCount;i++){
            ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt(i);
            if(i==index){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active)
                );

                }
            else{
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive)
                );

            }
            }

        if(index ==onboardingAdapter.getItemCount()-1){
            buttonOnboardingAction.setText("Start");
        }
        else{
            buttonOnboardingAction.setText("Next");

        }
        }

 }


