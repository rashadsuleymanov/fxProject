/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.naxpoct.bank.controller;

import animatefx.animation.Bounce;
import animatefx.animation.BounceIn;
import az.naxpoct.bank.model.Bank;
import az.naxpoct.bank.utility.AlertMessage;
import az.naxpoct.bank.utility.Entry3;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.dialog.ProgressDialog;

/**
 *
 * @author Rashad Suleymanov
 */
public class MainController implements Initializable {

    @FXML
    private JFXButton listBtnId;

    @FXML
    private JFXButton hesabatBtnİd;

    @FXML
    private JFXButton logoutBtnİd;

    @FXML
    private Pane pnHesabatCixar;

    @FXML
    private JFXButton umumiHesabatBtnId;

    @FXML
    private Label hesabatLabelProgressBar;

    @FXML
    private Label labelHesabatFileId;

    @FXML
    private Pane pnExcelList;

    @FXML
    private JFXButton excelFileListBtnId;

    @FXML
    private Label faylListLabelProgressBar;

    @FXML
    private Label labelListFileId;

    @FXML
    private Pane pnlStatusId;

    @FXML
    private Label lblStatusId;

    private Stage stage;

    Task listWork;

    Task hesabatWork;

    private DirectoryChooser chooser;

    private File folder;

    private DropShadow ds;

    public void setStage(Stage stage) {

        this.stage = stage;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hesabatBtnİd.disableProperty().set(true);
    }

    public Task createListWorker(int start, int end) {

        return new Task() {

            @Override
            protected Object call() throws Exception {

                for (int start = 0; start < end + 1; start++) {
                    Thread.sleep(10);
                    updateMessage(start + "/data");
                    updateProgress(start, end);

                }
                return true;
            }

        };

    }

    public Task createHesabatWorker(int start, int end) {

        return new Task() {

            @Override
            protected Object call() throws Exception {

                for (int start = 0; start < end; start++) {
                    Thread.sleep(10);
                    updateMessage(start + "/data");
                    updateProgress(start, end);

                }
                return true;
            }

        };

    }

    //MENU BUTTON CLICK
    @FXML
    public void handleClick(javafx.event.ActionEvent event) throws Exception {

        if (event.getSource() == listBtnId) {
            lblStatusId.setText("Faylları Listələmək");
            ds = new DropShadow();
            ds.setOffsetY(10.0);
            ds.setOffsetX(10.0);
            ds.setColor(Color.GRAY);

            pnlStatusId.setBackground(new Background(new BackgroundFill(Color.rgb(113, 86, 221), CornerRadii.EMPTY, Insets.EMPTY)));
            pnlStatusId.setEffect(ds);
            pnExcelList.setEffect(ds);
            pnExcelList.toFront();
            new BounceIn(pnlStatusId).play();
            new Bounce(pnExcelList).play();

        } else if (event.getSource() == hesabatBtnİd) {
            lblStatusId.setText("Ümumi Hesabat");
            ds = new DropShadow();
            ds.setOffsetY(10.0);
            ds.setOffsetX(10.0);
            ds.setColor(Color.GRAY);
            pnlStatusId.setBackground(new Background(new BackgroundFill(Color.rgb(43, 63, 99), CornerRadii.EMPTY, Insets.EMPTY)));
            pnlStatusId.setEffect(ds);
            pnHesabatCixar.setEffect(ds);
            pnHesabatCixar.toFront();
            new BounceIn(pnlStatusId).play();
            new Bounce(pnHesabatCixar).play();

        }

    }

    //EXCEL FAYL LIST CLICK
    @FXML
    public void handleExcelFayliListeleClick(javafx.event.ActionEvent actionEvent) {

        try {

            List<Bank> bankList = new ArrayList<Bank>();

            chooser = new DirectoryChooser();

            folder = chooser.showDialog(this.stage);;
            File files[];
            if (folder != null) {
                files = folder.listFiles();

                long say = 1;
                long faylSayi = 1;
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Message!");
                alert.setHeaderText("Excel Faylları Qəbul Olundu !");
                alert.setContentText("Məlumatları Listələmək Istədiyinizdən Əminsinizmi? ");

                Optional<ButtonType> optional = alert.showAndWait();

                if (optional.get().equals(ButtonType.OK)) {

                    if (files.length != 0) {

                        for (int i = 0; i < files.length; i++) {

                            if (files[i].getName().contains("~$")) {

                                AlertMessage.showMessage("Message", "Hal Hazırda Qovluq İçərsində Açıq Excel Faylı Var! Zəhmət Olmasa Bağlayın!", 6).showWarning();

                            } else {

                                FileInputStream inputStream = new FileInputStream(files[i]);

                                Workbook workbook = new XSSFWorkbook(inputStream);
                                Sheet sheet = workbook.getSheetAt(0);

                                for (int j = 9; j <= sheet.getLastRowNum() - 3; j++) {

                                    Row row = sheet.getRow(j);

                                    String id = row.getCell(1).getStringCellValue();
                                    long index = id.indexOf("AZN");
                                    id = id.substring(0, (int) index);

                                    long terminal = Long.parseLong(id);
                                    double debet = row.getCell(2).getNumericCellValue();
                                    double kredit = row.getCell(3).getNumericCellValue();
                                    String teyinat = row.getCell(4).getStringCellValue();

                                    Bank bank = new Bank(terminal, debet, kredit, teyinat);

                                    bankList.add(bank);

                                    Collections.sort(bankList);

                                    say++;
                                    listWork = createListWorker((int) say, (int) sheet.getLastRowNum() - 3);

                                    faylListLabelProgressBar.setText("Ümumi Data Sayı: " + String.valueOf(say));
                                }

                                ProgressDialog dialog = new ProgressDialog(listWork);
                                dialog.setContentText("Excel Fayl Adı: " + files[i].getName());

                                dialog.setTitle("Excel List");

                                dialog.setHeaderText("Fayl Sayı: " + faylSayi);

                                new Thread(listWork).start();
                                dialog.showAndWait();

                                faylSayi++;

                            }
                        }

                        XSSFWorkbook wb = new XSSFWorkbook();
                        XSSFSheet st = wb.createSheet("Bank List");
                        st.setDefaultColumnWidth(35);

                        CellStyle style = wb.createCellStyle();
                        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
                        style.setAlignment(CellStyle.ALIGN_CENTER);
                        Font font = wb.createFont();
                        font.setColor(IndexedColors.BLUE.getIndex());
                        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
                        font.setFontName("Arial");
                        style.setFont(font);

                        int rowIndex = 0;
                        Row headerRow = st.createRow(rowIndex++);
                        Cell cell = headerRow.createCell(0);
                        cell.setCellValue("Hesab No");
                        cell.setCellStyle(style);
                        Cell cell2 = headerRow.createCell(1);
                        cell2.setCellValue("DEBET");
                        cell2.setCellStyle(style);
                        Cell cell4 = headerRow.createCell(2);
                        cell4.setCellValue("KREDIT");
                        cell4.setCellStyle(style);
                        Cell cell5 = headerRow.createCell(3);
                        cell5.setCellValue("TEYINAT");
                        cell5.setCellStyle(style);

                        DataFormat format = wb.createDataFormat();
                        CellStyle style1 = wb.createCellStyle();
                        style1.setAlignment(CellStyle.ALIGN_CENTER);
                        style1.setDataFormat(format.getFormat("########################################"));
                        Font font1 = wb.createFont();
                        font1.setFontName("Calibri");
                        style1.setFont(font1);

                        for (Bank bank : bankList) {

                            Row row = st.createRow(rowIndex++);
                            int cellIndex = 0;
                            Cell cell7 = row.createCell(cellIndex++);
                            cell7.setCellValue(bank.getTerminalId());
                            cell7.setCellStyle(style1);
                            Cell cell8 = row.createCell(cellIndex++);
                            cell8.setCellValue(bank.getDebetId());
                            cell8.setCellStyle(style1);
                            Cell cell_10 = row.createCell(cellIndex++);
                            cell_10.setCellValue(bank.getKreditId());
                            cell_10.setCellStyle(style1);
                            Cell cell_11 = row.createCell(cellIndex++);
                            cell_11.setCellValue(bank.getTeyinatId());
                            //  cell_11.setCellStyle(style1);

                        }

                        LocalDate localDate = LocalDate.now();

                        String sysDate = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(localDate) + ".xlsx";

                        FileOutputStream out = new FileOutputStream(folder + "\\" + sysDate);
                        wb.write(out);
                        out.close();
                        AlertMessage.showMessage("Message", sysDate + " Faylı Yaradıldı!", 4).showInformation();

                        labelListFileId.setText("Proses Uğurla Həyata Keçirildi!");
                        excelFileListBtnId.disableProperty().set(true);
                        hesabatBtnİd.disableProperty().set(false);
                        AlertMessage.showMessage("Message", "Excel Dataları Sıralandı", 4).showInformation();

                    } else {

                        AlertMessage.showMessage("Message", "Hal Hazırda Qovluq Boşdur!", 3).showWarning();

                    }

                } else {

                    AlertMessage.showMessage("Message", "Faylları Listələməkdən İmtina Etdiniz!", 2).showWarning();

                }

            } else {

                AlertMessage.showMessage("Message", "Qovluq Seçimindən İmtina Etdiniz!", 2).showWarning();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //UMUMI HESABAT CLICK
    @FXML
    public void handleUmumiHesabatCixarClick(javafx.event.ActionEvent actionEvent) {

        try {
            int say = 1;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Message!");
            alert.setHeaderText("Excel Faylı Qəbul Olundu !");
            alert.setContentText("Hesabat Çıxarışını Etmək İstədiyinizə Əminsinizmi? ");

            Optional<ButtonType> optional = alert.showAndWait();

            if (optional.get().equals(ButtonType.OK)) {

                LocalDate localDate = LocalDate.now();

                String sysDate = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(localDate) + ".xlsx";

                FileInputStream inputStream = new FileInputStream(folder + "\\" + sysDate);

                XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
                Sheet sheet = workbook.getSheetAt(0);

                Workbook wb = new XSSFWorkbook();
                Sheet st = wb.createSheet("Bank Hesabat");
                st.setDefaultColumnWidth(19);

                List<Entry3<Long, Double, Double, String>> entryList = new ArrayList<>();

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                    Row row = sheet.getRow(i);

                    long terminal = (long) row.getCell(0).getNumericCellValue();
                    double debet = row.getCell(1).getNumericCellValue();
                    double kredit = row.getCell(2).getNumericCellValue();
                    String teyinat = row.getCell(3).getStringCellValue();

                    Entry3<Long, Double, Double, String> entry = new Entry3<Long, Double, Double, String>();
                    entry.setL(terminal);
                    entry.setK(debet);
                    entry.setV(kredit);
                    entry.setT(teyinat);

                    entryList.add(entry);

                    hesabatWork = createHesabatWorker((int) i, (int) sheet.getLastRowNum());

                    say++;
                    hesabatLabelProgressBar.setText("Ümumi Data Sayı: " + say);

                }

                ProgressDialog dialog = new ProgressDialog(hesabatWork);
                dialog.setContentText("Excel Fayl Adı: " + sysDate);

                dialog.setTitle("Excel Hesabat");

                dialog.setHeaderText("Files Loading...");

                new Thread(hesabatWork).start();
                dialog.showAndWait();

                List<Entry3<Long, Double, Double, String>> resultList = new ArrayList<>();
                String teyinatID = null;
                Long termID = null;
                Set<String> usedTeyIDList = new HashSet<String>();
                for (Entry3<Long, Double, Double, String> entry : entryList) {
                    teyinatID = entry.getT();

                    if (!usedTeyIDList.contains(teyinatID)) {
                        double sumDebet = 0.0;
                        double sumKredit = 0.0;
                        for (Entry3<Long, Double, Double, String> entryInner : entryList) {
                            if (teyinatID.equals(entryInner.getT())) {
                                termID = entryInner.getL();
                                sumDebet = sumDebet + entryInner.getK();
                                sumKredit = sumKredit + entryInner.getV();

                                //sum debit and sum kredit here
                            }
                        }

                        Entry3<Long, Double, Double, String> result = new Entry3<Long, Double, Double, String>();
                        result.setL(termID);
                        result.setK(sumDebet);
                        result.setV(sumKredit);
                        result.setT(teyinatID);
                        resultList.add(result);

                    } else {
                        continue;
                    }

                    usedTeyIDList.add(teyinatID);

                }

                AlertMessage.showMessage("Message", sysDate + " Faylı Yaradıldı!", 4).showInformation();

                CellStyle style = wb.createCellStyle();
                style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
                style.setAlignment(CellStyle.ALIGN_CENTER);
                Font font = wb.createFont();
                font.setColor(IndexedColors.BLUE.getIndex());
                font.setBoldweight(Font.BOLDWEIGHT_BOLD);
                font.setFontName("Arial");
                style.setFont(font);

                int rowIndex = 0;
                Row headerRow = st.createRow(rowIndex++);
                Cell cell = headerRow.createCell(0);
                cell.setCellValue("Hesab No");
                cell.setCellStyle(style);
                Cell cell2 = headerRow.createCell(1);
                cell2.setCellValue("DEBET");
                cell2.setCellStyle(style);
                Cell cell4 = headerRow.createCell(2);
                cell4.setCellValue("KREDIT");
                cell4.setCellStyle(style);
                Cell cell5 = headerRow.createCell(3);
                cell5.setCellValue("TEYINAT");
                cell5.setCellStyle(style);

                DataFormat format = wb.createDataFormat();
                CellStyle style1 = wb.createCellStyle();
                style1.setDataFormat(format.getFormat("########################################"));
                Font font1 = wb.createFont();
                font1.setFontName("Arial");
                style1.setFont(font1);

                for (Entry3<Long, Double, Double, String> entry : resultList) {

                    Row row = st.createRow(rowIndex++);

                    int cellIndex = 0;
                    Cell cell7 = row.createCell(cellIndex++);
                    cell7.setCellValue(entry.getL());
                    cell7.setCellStyle(style1);
                    Cell cell8 = row.createCell(cellIndex++);
                    cell8.setCellValue(entry.getK());
                    Cell cell_10 = row.createCell(cellIndex++);
                    cell_10.setCellValue(entry.getV());
                    Cell cell_11 = row.createCell(cellIndex++);
                    cell_11.setCellValue(entry.getT());
                    cell_11.setCellStyle(style1);
                }

                FileOutputStream out = new FileOutputStream(folder + "\\" + sysDate);
                wb.write(out);
                out.close();
                AlertMessage.showMessage("Message", "Excel Hesabatı Hazırlandı", 4).showInformation();
                labelHesabatFileId.setText("Proses Uğurla Həyata Keçirildi!");
                listBtnId.disableProperty().set(true);
                umumiHesabatBtnId.disableProperty().set(true);

            } else {

                AlertMessage.showMessage("Message", "Hesabat Çıxarışından İmtina Etdiniz!", 2).showWarning();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //LOGOUT CLICK
    @FXML
    public void handleLogoutClick(javafx.event.ActionEvent actionEvent) throws IOException {

        if (actionEvent.getSource() == logoutBtnİd) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Message!");
            alert.setHeaderText("Sistemdən Çıxış Etmək İstədiyinizə Əminsinizmi ?");

            Optional<ButtonType> optional = alert.showAndWait();

            if (optional.get().equals(ButtonType.OK)) {

                this.stage.close();

            }

        }

    }

    //MINIMAZE_STAGE
    @FXML
    private void minimaze_stage(MouseEvent event) {

        this.stage.setIconified(true);

    }

}
