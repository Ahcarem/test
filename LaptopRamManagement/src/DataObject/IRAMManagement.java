/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataObject;

import java.util.List;

/**
 *
 * @author giakh
 */
public interface IRAMManagement {
    void addRAMItem();
    void searchItems();
    List<RAMItem> loadFromFile();
    void updateRAMItem();
    void deleteRAMItem();
    void showAllRAMItems();
    
//    void loadFromFile();
}
