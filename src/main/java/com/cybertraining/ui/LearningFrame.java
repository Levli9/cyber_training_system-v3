package com.cybertraining.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.cybertraining.db.DatabaseManager;
import com.cybertraining.model.User;

public class LearningFrame extends JFrame {

    private List<String> slides = new ArrayList<>();
    private int current = 0;
    private JEditorPane contentArea;
    private JLabel progressLabel;
    private JButton prev;
    private JButton next;
    private JButton exam;

    public LearningFrame(DatabaseManager db, User user){

        setTitle("מסלול לימוד - אבטחת מידע");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GradientPanel bg = new GradientPanel(AppTheme.BG, AppTheme.BG2);
        bg.setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(15, 15, 15, 15));

        JButton backButton = AppTheme.backButton("← חזרה לתפריט");
        backButton.addActionListener(e -> {
            new EmployeeHomeFrame(db, user).setVisible(true);
            dispose();
        });
        
        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightHeader.setOpaque(false);
        rightHeader.add(backButton);
        
        header.add(rightHeader, BorderLayout.EAST);

        JLabel title = new JLabel("קורס יסודות אבטחת מידע");
        title.setForeground(AppTheme.TEXT);
        title.setFont(AppTheme.TITLE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(title, BorderLayout.CENTER);

        bg.add(header, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(new EmptyBorder(10, 40, 20, 40));

        contentArea = new JEditorPane();
        contentArea.setContentType("text/html");
        contentArea.setEditable(false);
        contentArea.setBackground(AppTheme.CARD);
        contentArea.setForeground(AppTheme.TEXT);
        contentArea.setBorder(new EmptyBorder(30, 30, 30, 30));

        JScrollPane scroll = new JScrollPane(contentArea);
        scroll.setBorder(AppTheme.cardPanel().getBorder());
        scroll.setBackground(AppTheme.BG);
        scroll.getViewport().setBackground(AppTheme.CARD);
        
        centerPanel.add(scroll, BorderLayout.CENTER);
        bg.add(centerPanel, BorderLayout.CENTER);

        JPanel nav = new JPanel(new BorderLayout());
        nav.setOpaque(false);
        nav.setBorder(new EmptyBorder(10, 40, 30, 40));

        progressLabel = new JLabel();
        progressLabel.setForeground(AppTheme.MUTED);
        progressLabel.setFont(AppTheme.TEXT_FONT);
        progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonsPanel.setOpaque(false);

        prev = AppTheme.secondaryButton("שקף קודם");
        next = AppTheme.primaryButton("שקף הבא");
        exam = AppTheme.primaryButton("מעבר למבחן");

        prev.addActionListener(e -> prev());
        next.addActionListener(e -> next());
        exam.addActionListener(e -> {
            new QuizFrame(db, user).setVisible(true);
            dispose();
        });

        buttonsPanel.add(prev);
        buttonsPanel.add(exam);
        buttonsPanel.add(next);

        nav.add(progressLabel, BorderLayout.NORTH);
        nav.add(Box.createVerticalStrut(15), BorderLayout.CENTER);
        nav.add(buttonsPanel, BorderLayout.SOUTH);

        bg.add(nav, BorderLayout.SOUTH);

        add(bg);

        loadSlides();
        showSlide();
    }

    private String htmlWrap(String content) {
        return "<html><body dir='rtl' style='font-family: Arial, sans-serif; font-size: 16px; color: #E6E8EE; line-height: 1.6;'>" + content + "</body></html>";
    }

    private void loadSlides(){

        slides.add(htmlWrap(
            "<h1 style='color: #54A0FF; border-bottom: 2px solid #484E60; padding-bottom: 10px;'>👋 מבוא לאבטחת מידע בארגונים</h1>" +
            "<p>אבטחת מידע היא התחום שנועד להגן על מערכות מחשוב, רשתות ומידע מפני גישה בלתי מורשית, גניבה, נזק או חבלה.</p>" +
            "<div style='background-color:#2a2d3d; padding:15px; border-radius:10px; border-right:5px solid #FF9F43;'>" +
            "<h3 style='color:#FF9F43; margin-top:0;'>💡 נתון מפתח</h3>" +
            "בשנת 2023 בלבד, העלות הממוצעת העולמית של פריצת נתונים לחברה עמדה על למעלה מ-<b>4.45 מיליון דולר</b> (לפי דו\"ח פעילות הסייבר של IBM). רוב התקיפות מתחילות מהגורם האנושי - <span style='color:#ffffaa;'>אנחנו!</span></div>" +
            "<p><b>בקורס זה נלמד על:</b></p>" +
            "<ul style='line-height:1.8;'>" +
            "<li><span style='color:#3498db'>🎣 פישינג (Phishing)</span> - הסכנה במיילים מזויפים</li>" +
            "<li><span style='color:#e67e22'>🎭 הנדסה חברתית (Social Engineering)</span> - הפסיכולוגיה של הפריצה</li>" +
            "<li><span style='color:#e74c3c'>🦠 תוכנות זדוניות וסחיטה (Ransomware)</span> - הצפנת קבצים וחומרות</li>" +
            "<li><span style='color:#2ecc71'>📛 סיסמאות והזדהות בטוחה</span> - קביעת שכבות אימות</li>" +
            "<li><span style='color:#9b59b6'>🏢 אבטחה פיזית בסביבת העבודה</span> - נהלי שולחן נקי ונעילת ציוד</li>" +
            "<li><span style='color:#1abc9c'>🌐 גלישה בטוחה ורשתות ציבוריות צד ג'</span> - VPN, רשתות קפה ואלחוט</li>" +
            "</ul>"
        ));

        slides.add(htmlWrap(
            "<h1 style='color: #3498db; border-bottom: 2px solid #3498db; padding-bottom: 10px;'>🎣 פישינג (Phishing) וזיוף זהות</h1>" +
            "<p><b>פישינג</b> (דיוג) היא שיטת תקיפה שבה התוקף מתחזה לגורם לגיטימי (כמו בנק, מנהל בכיר, או שירות מוכר) כדי לגרום לכם לחשוף מידע רגיש, ללחוץ על קישור זדוני או להוריד קובץ מסוכן. מעל ל-<b>3.4 מיליארד הודעות זבל (ספאם)</b> נשלחות מדי יום ברחבי העולם.</p>" +
            "<div style='background-color:#2a2d3d; padding:15px; border-radius:10px; border-right:5px solid #e74c3c;'>" +
            "<h3 style='color:#e74c3c; margin-top:0;'>⚠️ נורות אזהרה (דגלים אדומים):</h3>" +
            "<ul>" +
            "<li><b>תחושת דחיפות / איום:</b> \"חשבונך יינעל תוך 24 שעות במידה ולא תאשר את פרטי האשראי שלך עכשיו!\"</li>" +
            "<li><b>שגיאות כתיב וניסוח משונה:</b> תרגום קלוקל מ-Google Translate או שפה לא שגרתית ממנהל מוכר.</li>" +
            "<li><b>התחזות (Spoofing) בשולח:</b> בדקו תמיד את כתובת המייל האמיתית ליד השם. לדוגמה <span style='font-family: monospace; background:#000; padding:2px;'>support@paypa1.com</span> (שימו לב לספרה 1 במקום l ב-paypal).</li>" +
            "<li><b>בקשה פתאומית למידע רגיש:</b> נציגי משאבי אנוש, בנקים או IT <b style='color:#FF5E5E;'>לעולם אינם</b> מבקשים את הסיסמה שלכם במייל.</li>" +
            "</ul></div>" +
            "<p><b>💡 טיפ זהב:</b> אל תלחצו על הקישור במייל! אם חשבונכם בסכנה על פי ההודעה, פתחו דפדפן, והקלידו בעצמכם את כתובת האתר האמיתית.</p>"
        ));

        slides.add(htmlWrap(
            "<h1 style='color: #e67e22; border-bottom: 2px solid #e67e22; padding-bottom: 10px;'>🎭 הנדסה חברתית (Social Engineering)</h1>" +
            "<p>הנדסה חברתית היא \"פריצה לאנשים, במקום למחשבים\". במקום לבזבז חודשים בפיצוח חומות אש, התוקפים מנצלים פסיכולוגיה אנושית טבעית: פחד מסמכות, אדיבות, סקרנות ורצון לעזור.</p>" +
            "<div style='background-color:#2a2d3d; padding:15px; border-radius:10px; border-right:5px solid #f1c40f;'>" +
            "<h3 style='color:#f1c40f; margin-top:0;'>🎭 שיטות נפוצות שמטרגטות אתכם אישית:</h3>" +
            "<ul>" +
            "<li><b>Pretexting (שימוש בתרחיש שקרי):</b> תוקף מתקשר בטון לחוץ וצועק: \"מדבר סמנכ\"ל הכספים! אני בפגישה סגורה וצריך העברה דחופה של דוח הסליקה לעוד 5 דקות! תמשיך לי לכתובת ג'ימייל פרטית!\"</li>" +
            "<li><b>Baiting (פיתיון סקרנות):</b> השארת Disk-On-Key נגוע בחניון החברה עם מדבקה שכתוב עליה: <i style='color:#EE5253'>\"משכורות ובונוסים בכירים 2024\"</i>. אדם מסוקרן שמכניס זאת יריץ קוד זדוני בארגון מיד.</li>" +
            "<li><b>Tailgating (השתחלות למשרד):</b> אדם זר ולבוש היטב מגיע מאחוריכם בכניסה עם כוסות קפה בידיו ומבקש: \"הי, תוכל להחזיק לי את הדלת? שכחתי את התג למעלה\".</li>" +
            "</ul></div>" +
            "<p><b>מוסר השכל:</b> עזרו לחברים לעבודה, אבל אם לא זיהיתם אותם או נשמעת בקשה חריגה שוברת-נהלים - עצרו הכל, אמתטו מול גורם מוסמך, ואל תפעלו מתוך לחץ.</p>"
        ));

        slides.add(htmlWrap(
            "<h1 style='color: #e74c3c; border-bottom: 2px solid #e74c3c; padding-bottom: 10px;'>🦠 תוכנות זדוניות וסחיטה (Ransomware)</h1>" +
            "<p><b>Ransomware (כופרה)</b> היא סוג של תוכנה זדונית המצפינה באופן בלתי הפיך (ללא מפתח) את כל המסמכים במחשב שלכם, ובמהירות זולגת גם לשרתי הרשת הארגונית השלמה. <br>בסיום ההצפנה הקטלנית, היא מציגה הודעת דרישה לתשלום כופר (במטבעות קריפטו) בתוך 48 שעות, אחרת הקבצים יושמדו.</p>" +
            "<p><i>עלות הנזק למשק העולמי מכופרות בלבד הוערך ב-<b>20 מיליארד דולר</b>.</i> הופלו בתי חולים, מתקני אספקת חשמל ומערכות כספים ממשלתיות לחלוטין!</p>" +
            "<h3>סוגי נוזקה שעליכם להכיר:</h3>" +
            "<ul>" +
            "<li><b>סוסים טרויאניים (Trojan Horse):</b> קובץ או תוכנה שנראית כמו משחק חינמי (למשל סדק לתוכנה, קובץ PDF של קורות חיים) - אבל ברקע מתקינה וירוסים.</li>" +
            "<li><b>Keyloggers:</b> נוזקות סמויות שלוכדות כל הקשה, גניבת סיסמה במקלדת, או מספר כרטיס אשראי, ושולחות לתוקף מרחוק.</li>" +
            "</ul>" +
            "<p><b>🛡️ כיצד להתגונן?</b> אל תורידו תוכנות פיראטיות (Torrents / Cracks)! ודאו שאייקון האנטי-וירוס דולק אצלכם.</p>"
        ));

        slides.add(htmlWrap(
            "<h1 style='color: #2ecc71; border-bottom: 2px solid #2ecc71; padding-bottom: 10px;'>📛 בניית סיסמאות חזקות</h1>" +
            "<p>לפי חברת NordPass, הסיסמה הנפוצה בעולם גם ב-2023 הייתה <i>'123456'</i>. סיסמאות אחרות של 8 תווים נטולות פיסוק עשויות להפרץ בתוכנה אוטומטית (Brute-Force) תוך מספר שניות.</p>" +
            "<div style='background-color:#2a2d3d; padding:15px; border-radius:10px; border-right:5px solid #2ecc71;'>" +
            "<h3 style='color:#2ecc71; margin-top:0;'>✔ כללי ברזל לסיסמה מנצחת:</h3>" +
            "<ul>" +
            "<li>אורך משמעותי: לפחות 12 תווים. שיטה מעולה היא \"משפט סיסמה\" שקל לכם לזכור: <i>I-Love-P1tza-In-Tel-Aviv!</i></li>" +
            "<li>שלבו הכל מרשימת התערובת: אותיות גדולות (<span style='color:#54A0FF'>a..z</span>), קטנות (<span style='color:#54A0FF'>A..Z</span>), ספרות (<span style='color:#54A0FF'>0..9</span>) וסימנים מיוחדים (<span style='color:#54A0FF'>@!#&?*</span>).</li>" +
            "<li><b>מחזור סיסמאות הוא מסוכן:</b> מיחזור סיסמה לכל האתרים פירושו שאם פרצו אתר פורום כלבים בו נרשמתם, ההאקר ינסה מיידית את אותה הסיסמה למייל שלכם בארגון - והוא יכנס בשמכם!</li>" +
            "</ul></div>" +
            "<p><b>⭐️ אימות רב-שלבי (MFA / 2FA):</b></p>" +
            "<p>השכבה שמונעת פריצה גם אם גנבו לכם את הסיסמה לחלוטין. כל התחברות תחייב מרכיב נוסף שרק ברשותכם (לדוגמה קוד אימות חד-פעמי למסרון בטלפון הנייד, או אישור באפליקציית Authenticator).</p>"
        ));

        slides.add(htmlWrap(
            "<h1 style='color: #9b59b6; border-bottom: 2px solid #9b59b6; padding-bottom: 10px;'>🏢 אבטחה פיזית ונהלי שולחן נקי</h1>" +
            "<p>הגנה על המידע הפיזי של החברה חשובה בדיוק כמו אבטחה ברשת. תארו לכם פורץ פיזי שנכנס לבניין ולוקח לפטופ פועל ללא צורך ואפילו לפרוץ סיסמה!</p>" +
            "<ul>" +
            "<li><b>💻 נעילת עמדה (Screen Lock):</b> בכל פעם שאתם קמים מהכיסא, ולוא לדקה לשירותים או להביא כוס מים, חובה לנעול את מסך העבודה (באמצעות קומבינצית <b style='color:#fff'>Win + L</b> במחשב PC, או <b style='color:#fff'>Control+Command+Q</b> במק).</li>" +
            "<li><b>📄 מדיניות שולחן נקי (Clean Desk Policy):</b> אל תשאירו פתקיות עם סיסמאות מתחת למקלדת או חוזים וטופסי לקוחות בגלוי. בסוף היום העבירו הכל למגירה נעולה או מגרסה ארגונית מחמירה.</li>" +
            "<li><b>👀 גלישת-כתף (Shoulder Surfing):</b> היו מודעים לסביבה שלכם! כשאתם כותבים טבלת לקוחות רגישה ברכבת, המושב מאחוריכם עלול לצלם זאת בסמארטפון בקלות. מומלץ להצטייד במגן מסך המסתיר צפייה מהצד (Privacy Filter).</li>" +
            "</ul>"
        ));

        slides.add(htmlWrap(
            "<h1 style='color: #1abc9c; border-bottom: 2px solid #1abc9c; padding-bottom: 10px;'>🌐 גלישה בטוחה ורשתות ציבוריות</h1>" +
            "<p>כשאנו עובדים מרחוק בבתי קפה, מלונות או כנסים פומביים בחו\"ל, אנו חשופים לפגיעות עצומה לרשת של איסוף-תעבורה (Man it The Middle Attack).</p>" +
            "<div style='background-color:#2a2d3d; padding:15px; border-radius:10px; border-right:5px solid #1abc9c;'>" +
            "<h3>סכנת רשתות Wi-Fi חינמיות (Public Wi-Fi):</h3>" +
            "<p>אל תיכנסו מעולם לבנק, ולא למאגרי החברה ברשת פתוחה חסרת סיסמה (כמו \"Free_Starbucks_WiFi\"). כל מה שתשלחו באותה רשת (הודעות, קבצים, סיסמאות לא מוצפנות) נראים בטקסט פשוט והאקר יכול לשאוב אותם אם הוא יושב שולחן על ידכם.</p></div>" +
            "<h3>הפיתרון המעשי - שימוש ב־VPN</h3>" +
            "<p>לקוח ה-VPN היושב במחשב, עוטף את כל האינטרנט היוצא מהמחשב שלכם בצנרת פלדיום מוצפנת היטב ומשגרו בעילום שם ישירות לשרתי המשרד. כך לאיש בבית הקפה אין יכולת לפרש מה אתם עושים.</p>"
        ));

        slides.add(htmlWrap(
            "<h1 style='color: #f1c40f; text-align: center; font-size: 34px;'>🏆 סיכום הקורס: שגרירי הסייבר</h1>" +
            "<p style='text-align:center; font-size:20px;'>בסופו של דבר, רוב המתקפות לעולם יעצרו מול תגובה אחראית של עובד שזיהה את הסכנה בזמן אמת.</p>" +
            "<div style='margin-left: 20%; margin-right: 20%; font-size: 18px;'>" +
            "<ul>" +
            "<li>✔️ <b>חשדו בכל פניה לא שגרתית.</b> קראו כל מייל בשבע עיניים.</li>" +
            "<li>✔️ <b>היזהרו מהנדסה חברתית.</b> אל תשתפו פרטים בעל פה בלי לאמת זהות.</li>" +
            "<li>✔️ <b>בטחו את הזהות שלכם.</b> סיסמאות חזקות שונות + הפעלת אימות כפול (MFA) בכל רשת!</li>" +
            "<li>✔️ <b>נעלו והשמידו.</b> נעילת תצוגה ביציאה לעולם, גריסת מסמכים.</li>" +
            "<li>✔️ <b style='color:#FF5E5E;'>דווחו מייד למערך התמיכה (IT).</b> אם לחצתם בטעות? זה קורה לכולם! דיווח תוך 5 דקות עשוי להציל את החברה כולה. לא יכעסו עליכם שהודיתם בטעות במועד.</li>" +
            "</ul></div>" +
            "<br><center><div style='background-color:#2a2d3d; padding:20px; border-radius:10px; width:70%; margin:auto;'>" +
            "<span style='color:#54A0FF; font-weight:bold; font-size: 26px;'>אתם חומת המגן שלנו!</span><br><br>" +
            "אם קראתם הכל בהצלחה - הגיע הזמן לבחון אתכם. <b>לחצו על 'מעבר למבחן' מימין למטה.</b> 🎓</div></center>"
        ));

    }

    private void showSlide(){
        contentArea.setText(slides.get(current));
        progressLabel.setText("שקף " + (current + 1) + " מתוך " + slides.size());

        prev.setVisible(current > 0);
        if(current == slides.size() - 1){
            next.setVisible(false);
            exam.setVisible(true);
        } else {
            next.setVisible(true);
            exam.setVisible(false);
        }
    }

    private void next(){
        if(current < slides.size()-1){
            current++;
            showSlide();
        }
    }

    private void prev(){
        if(current > 0){
            current--;
            showSlide();
        }
    }
}