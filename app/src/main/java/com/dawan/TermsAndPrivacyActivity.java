package com.dawan;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dawan.databinding.ActivityCreateAccountBinding;
import com.dawan.databinding.ActivityTermsAndPrivacyBinding;

import java.io.IOException;
import java.io.InputStream;

public class TermsAndPrivacyActivity extends AppCompatActivity {
    ActivityTermsAndPrivacyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityTermsAndPrivacyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.textView.setOnClickListener(v->{
            getOnBackPressedDispatcher().onBackPressed();
        });
        if(getIntent().getStringExtra("tORp").equals("t")){
            binding.textView.setText("Terms & Conditions");
            loadWordFileIntoWebView(getTermsContent());
        }
        else  if(getIntent().getStringExtra("tORp").equals("p")) {
            binding.textView.setText("Privacy & Policy");
            loadWordFileIntoWebView(getPrivacyContent());
        }
        else {
            Toast.makeText(this, "Unspecified File", Toast.LENGTH_SHORT).show();
        }

    }

    private String getPrivacyContent() {
        String content="<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>Welcome to <strong>DAWAN</strong>! We are committed to protecting your privacy and ensuring the security of your personal information. By using our app, you consent to the collection, use, and disclosure of your information as described in this Privacy Policy.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>&nbsp;</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>Information We Collect</strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>&nbsp;Personal Information</strong>: We may collect personal information such as your name, email address, and location when you register an account or subscribe to our services.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>Usage Information:</strong> We collect information about how you interact with our app, including articles read, pages visited, and features used.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>&nbsp;</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>How We Use Your Information</strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>Personalization:</strong> We use your information to personalize your news feed and deliver relevant content based on your interests and preferences.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>Communication:</strong> We may use your contact information to send you newsletters, updates, and promotional offers.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>Analytics:</strong> We analyze user data to improve our app&apos;s performance, optimize user experience, and enhance our services.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>&nbsp;</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>Data Sharing</strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>Third-party Partners</strong>: We may share your information with third-party partners and service providers to facilitate app functionality, analytics, and marketing efforts.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>Legal Compliance:</strong> We may disclose your information in response to legal requests, such as subpoenas or court orders, or to comply with applicable laws and regulations.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>&nbsp;</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>Data Security</strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>We implement industry-standard security measures to protect your information from unauthorized access, disclosure, alteration, or destruction.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>&nbsp;</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>Your Choices</strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>Opt-out:</strong> You can opt out of receiving promotional emails or newsletters by following the unsubscribe instructions included in the email.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>Account Access:</strong> You can access, update, or delete your account information by logging into your account settings.<br>&nbsp;</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>Children&apos;s Privacy</strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>Our app is not intended for children under the age of 13. We do not knowingly collect personal information from children without parental consent.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>Changes to Privacy Policy</strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>We reserve the right to update or modify this Privacy Policy at any time. We will notify you of any changes by posting the revised policy on our website or app.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>&nbsp;</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>Contact Us</strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>&nbsp;</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>If you have any questions or concerns about our Privacy Policy or data practices, please contact us at <a href=\"mailto:info@dawan.so\">info@dawan.so</a> .</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>&nbsp;</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>Thank you for using <strong>DAWAN.</strong> Your privacy is important to us.</p>";
        return content;
    }

    private String getTermsContent() {
        String content="<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>Welcome to <strong>DAWAN</strong>! By accessing or using our app, you agree to be bound by the following terms and conditions:</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>&nbsp;</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>1. Acceptance of Terms</strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>By downloading, installing, or using DAWAN you acknowledge that you have read, understood, and agree to comply with these terms and conditions. If you do not agree with any part of these terms, you must not use our app.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>&nbsp;</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>2. Use of the App</strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>You must be at least 13 years old to use DAWAN.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>You are responsible for maintaining the confidentiality of your account credentials and for all activities &nbsp; that occur under your account.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>&nbsp;</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>3. Content Usage</strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>The content provided by DAWAN is for personal, non-commercial use only.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>You may not reproduce, distribute, modify, or republish any content from the app without prior written permission.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>&nbsp;</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>4. User Conduct</strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>You agree not to use DAWAN for any unlawful or prohibited purpose.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>You must not engage in any activity that disrupts or interferes with the functioning of the app or its services.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>&nbsp;</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>5. Intellectual Property</strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>All content, trademarks, logos, and intellectual property rights associated with DAWAN are owned by us or our licensors.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>&nbsp;You may not use any trademarks or logos displayed on the app without our prior written consent.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>&nbsp;</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>6. Limitation of Liability</strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>DAWAN and its affiliates shall not be liable for any indirect, incidental, special, or consequential damages arising out of or in connection with the use of the app.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>&nbsp;</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>7. Termination</strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>We reserve the right to suspend or terminate your access to DAWAN at any time without prior notice for any reason, including violation of these terms and conditions.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>&nbsp;</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>8. Changes to Terms</strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>We reserve the right to update or modify these terms and conditions at any time. Any changes will be effective immediately upon posting on the app.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>&nbsp;</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'><strong>9. Governing Law</strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>These terms and conditions shall be governed by and construed in accordance with the laws of Somali Jurisdiction.</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>&nbsp;</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>10. Contact Information</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>If you have any questions or concerns regarding these terms and conditions, please contact us at <a href=\"mailto:info@dawan.so\">info@dawan.so</a> .</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>&nbsp;</p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;font-size:11.0pt;font-family:\"Calibri\",sans-serif;'>Thank you for using DAWAN. Enjoy staying informed!</p>";
    return content;
    }

    private void loadWordFileIntoWebView(String content) {
        binding.wv.loadData(content, "text/html", "UTF-8");
    }
}