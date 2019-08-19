/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.naxpoct.bank.utility;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Rashad Suleymanov
 */
public class AlertMessage {

    public static Notifications showMessage(String title, String text, long seconds) {

        Notifications notification = Notifications.create()
                .title(title)
                .text(text)
                .position(Pos.TOP_CENTER)
                .darkStyle()
                .hideAfter(Duration.seconds(seconds));
        return notification;
    }
}
