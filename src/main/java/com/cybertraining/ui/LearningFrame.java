package com.cybertraining.ui;

import com.cybertraining.db.DatabaseManager;
import com.cybertraining.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LearningFrame extends JFrame {

    private List<String> slides = new ArrayList<>();

    private int current = 0;

    private JTextArea contentArea;

    public LearningFrame(DatabaseManager db, User user){

        setTitle("מסלול לימוד - אבטחת מידע");
        setSize(900,600);
        setLocationRelativeTo(null);

        GradientPanel bg = new GradientPanel(AppTheme.BG,AppTheme.BG2);
        bg.setLayout(new BorderLayout());

        JLabel title = new JLabel("קורס יסודות אבטחת מידע");
        title.setForeground(AppTheme.TEXT);
        title.setFont(AppTheme.TITLE);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        bg.add(title,BorderLayout.NORTH);

        contentArea = new JTextArea();

        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);

        contentArea.setBackground(AppTheme.CARD);
        contentArea.setForeground(AppTheme.TEXT);
        contentArea.setFont(AppTheme.TEXT_FONT);

        JScrollPane scroll = new JScrollPane(contentArea);

        bg.add(scroll,BorderLayout.CENTER);

        JPanel nav = new JPanel();

        JButton prev = AppTheme.secondaryButton("שקף קודם");
        JButton next = AppTheme.primaryButton("שקף הבא");
        JButton exam = AppTheme.primaryButton("מעבר למבחן");

        prev.addActionListener(e->prev());
        next.addActionListener(e->next());

        exam.addActionListener(e->{

            new QuizFrame(db,user).setVisible(true);
            dispose();

        });

        nav.add(prev);
        nav.add(next);
        nav.add(exam);

        bg.add(nav,BorderLayout.SOUTH);

        add(bg);

        loadSlides();
        showSlide();
    }

    private void loadSlides(){

        slides.add(
        "מהי אבטחת מידע?\n\n"+
        "אבטחת מידע היא תחום שמטרתו להגן על מידע, מערכות מחשב ורשתות\n"+
        "מפני גישה לא מורשית, שימוש לא תקין או פגיעה במידע.\n\n"+
        "בארגונים מודרניים מידע הוא אחד הנכסים החשובים ביותר ולכן\n"+
        "יש צורך להגן עליו באמצעים שונים."
        );

        slides.add(
        "סיסמאות חזקות\n\n"+
        "סיסמה חזקה צריכה לכלול:\n"+
        "- אותיות גדולות וקטנות\n"+
        "- מספרים\n"+
        "- סימנים מיוחדים\n\n"+
        "אין להשתמש בסיסמאות כמו:\n"+
        "123456\n"+
        "password\n"+
        "תאריך לידה"
        );

        slides.add(
        "מתקפת Phishing\n\n"+
        "Phishing היא אחת המתקפות הנפוצות ביותר.\n\n"+
        "התוקף מתחזה לגורם אמין כמו:\n"+
        "- בנק\n"+
        "- מנהל\n"+
        "- מערכת IT\n\n"+
        "ומבקש מהמשתמש להזין סיסמה או מידע רגיש."
        );

        slides.add(
        "הנדסה חברתית\n\n"+
        "הנדסה חברתית היא שיטה שבה התוקף מנצל\n"+
        "את האמון של העובד כדי לקבל מידע.\n\n"+
        "לדוגמה:\n"+
        "טלפון מתחזה מהתמיכה הטכנית."
        );

        slides.add(
        "סיכום\n\n"+
        "כדי לשמור על אבטחת מידע בארגון:\n\n"+
        "✔ השתמש בסיסמאות חזקות\n"+
        "✔ אל תלחץ על קישורים חשודים\n"+
        "✔ אל תכניס התקני USB לא מוכרים\n"+
        "✔ דווח על פעילות חשודה"
        );

    }

    private void showSlide(){

        contentArea.setText(slides.get(current));

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