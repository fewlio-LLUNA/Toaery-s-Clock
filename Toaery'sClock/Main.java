import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main extends Object {

    private static String[] imageFiles = { "1.png", "2.png", "3.png" };
    private static int currentImageIndex = 0;

    public static void main(String[] args) {

        // ウィンドウの準備
        JFrame mainFrame = new JFrame("Toaery's Clock");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(428, 428);
        mainFrame.setResizable(false); // 画面サイズを固定

        // ファビコンの設定
        try {
            Image favicon = ImageIO.read(new File("favicon.png"));
            mainFrame.setIconImage(favicon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // コンテンツパネルの準備
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.decode("#A2BE83")); // 背景色の設定
        contentPanel.setLayout(null); // レイアウトマネージャを無効

        // 時計ラベルの設定
        JLabel dateLabel = new JLabel();
        Font dotGothicFont = loadFont("DotGothic16-Regular.ttf", Font.PLAIN, 40);
        dateLabel.setFont(dotGothicFont);
        dateLabel.setForeground(Color.decode("#3F564D")); // 文字色の設定
        dateLabel.setBounds(26, 10, 400, 78);
        contentPanel.add(dateLabel);

        // 画像表示用ラベルの設定
        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(20,20,400,400);
        contentPanel.add(imageLabel);

        // ウィンドウにコンテンツパネルを設定
        mainFrame.add(contentPanel);

         // ウィンドウの中央に表示する
         mainFrame.setLocationRelativeTo(null);

        // ウィンドウの表示
         mainFrame.setVisible(true);

        // 時刻更新処理の定義
        TimerTask aTimerTask = new TimerTask() {
            public void run() {
                Date aDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd(E) HH:mm ss");
                String formattedDate = dateFormat.format(aDate);
                dateLabel.setText(formattedDate);
            }
        };

        // 画像表示処理の定義
        TimerTask imageTask = new TimerTask() {
            public void run() {
                try {
                    Image image = ImageIO.read(new File(imageFiles[currentImageIndex]));
                    imageLabel.setIcon(new ImageIcon(image));
                    currentImageIndex = (currentImageIndex + 1) % imageFiles.length;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        // 時刻更新を１秒ごとに実行
        Timer aTimer = new Timer();
        aTimer.scheduleAtFixedRate(aTimerTask, 0, 1000);

        // 画像表示を１秒ごとに実行
        Timer imageTimer = new Timer();
        imageTimer.scheduleAtFixedRate(imageTask, 0, 1000);

    }

    // フォントをロードするメソッド
    private static Font loadFont(String filename, int style, float size) {
        Font font = null;
        try {
            File fontFile = new File(filename);
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(style, size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            // フォントのロードに失敗した場合はデフォルトのフォントを使用
            font = new Font(Font.SANS_SERIF, style, (int) size);
        }
        return font;
    }

}
