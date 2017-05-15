/*
   JAdventure - A Java-based RPG
   Copyright (C) 2017  TehGuy
 
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
 
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
 
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package ui;

import logic.core.RandomNumberGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class CharacterCreationFrame extends JInternalFrame {
    private JLabel lblAbsStr, lblAbsDex, lblAbsCon, lblAbsInt, lblAbsWis, lblAbsCha;
    private JLabel lblAbsStrMod, lblAbsDexMod, lblAbsConMod, lblAbsIntMod, lblAbsWisMod, lblAbsChaMod;
    private JLabel lblAbsStrRaceMod, lblAbsDexRaceMod, lblAbsConRaceMod, lblAbsIntRaceMod, lblAbsWisRaceMod, lblAbsChaRaceMod;

    private JComboBox<String> cboRace, cboRollType;

    private JCheckBox chkStrRaceMod, chkDexRaceMod, chkConRaceMod, chkIntRaceMod, chkWisRaceMod;

    private JPanel mainPanel;

    private int halfElfMod = 2;
    private int str, dex, con, intel, wis, cha;

    CharacterCreationFrame(){
        super("Character Creation", false, true, false, true);
        setSize(572, 660);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setOpaque(true);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(null);

        initLabels();
        initAbsLabels();
        initCboBoxes();
        initButtons();
        initChkBoxes();

        add(mainPanel);
        setVisible(true);
    }

    private void initLabels(){
        JLabel label1 = new JLabel("Strength: ");
        label1.setLocation(34, 102);
        label1.setSize(79, 20);
        mainPanel.add(label1);

        JLabel label2 = new JLabel("Dexterity: ");
        label2.setLocation(34, 142);
        label2.setSize(79, 20);
        mainPanel.add(label2);

        JLabel label3 = new JLabel("Constitution: ");
        label3.setLocation(34, 182);
        label3.setSize(98, 20);
        mainPanel.add(label3);

        JLabel label4 = new JLabel("Intelligence: ");
        label4.setLocation(34, 222);
        label4.setSize(94, 20);
        mainPanel.add(label4);

        JLabel label5 = new JLabel("Wisdom: ");
        label5.setLocation(34, 262);
        label5.setSize(70, 20);
        mainPanel.add(label5);

        JLabel label6 = new JLabel("Charisma: ");
        label6.setLocation(34, 302);
        label6.setSize(80, 20);
        mainPanel.add(label6);

        JLabel label7 = new JLabel("Ability Score");
        label7.setLocation(280, 57);
        label7.setSize(96, 20);
        mainPanel.add(label7);

        JLabel label8 = new JLabel("ABS Mod");
        label8.setLocation(408, 57);
        label8.setSize(77, 20);
        mainPanel.add(label8);

        JLabel label9 = new JLabel("Racial Mod");
        label9.setLocation(147, 57);
        label9.setSize(88, 20);
        mainPanel.add(label9);

        JLabel label10 = new JLabel("Race: ");
        label10.setLocation(34, 388);
        label10.setSize(51, 20);
        mainPanel.add(label10);

        JLabel label11 = new JLabel("Roll Type:");
        label11.setLocation(111, 506);
        label11.setSize(78, 20);
        mainPanel.add(label11);
    }

    private void initAbsLabels(){
        Dimension emptyDim = new Dimension(20, 20);

        lblAbsStr = new JLabel("0", JLabel.CENTER);
        lblAbsStr.setLocation(326, 102);
        lblAbsStr.setSize(emptyDim);
        mainPanel.add(lblAbsStr);

        lblAbsDex = new JLabel("0", JLabel.CENTER);
        lblAbsDex.setLocation(326, 142);
        lblAbsDex.setSize(emptyDim);
        mainPanel.add(lblAbsDex);

        lblAbsCon = new JLabel("0", JLabel.CENTER);
        lblAbsCon.setLocation(326, 182);
        lblAbsCon.setSize(emptyDim);
        mainPanel.add(lblAbsCon);

        lblAbsInt = new JLabel("0", JLabel.CENTER);
        lblAbsInt.setLocation(326, 222);
        lblAbsInt.setSize(emptyDim);
        mainPanel.add(lblAbsInt);

        lblAbsWis = new JLabel("0", JLabel.CENTER);
        lblAbsWis.setLocation(326, 262);
        lblAbsWis.setSize(emptyDim);
        mainPanel.add(lblAbsWis);

        lblAbsCha = new JLabel("0", JLabel.CENTER);
        lblAbsCha.setLocation(326, 302);
        lblAbsCha.setSize(emptyDim);
        mainPanel.add(lblAbsCha);

        lblAbsStrMod = new JLabel("0", JLabel.CENTER);
        lblAbsStrMod.setLocation(442, 102);
        lblAbsStrMod.setSize(emptyDim);
        mainPanel.add(lblAbsStrMod);

        lblAbsDexMod = new JLabel("0", JLabel.CENTER);
        lblAbsDexMod.setLocation(442, 142);
        lblAbsDexMod.setSize(emptyDim);
        mainPanel.add(lblAbsDexMod);

        lblAbsConMod = new JLabel("0", JLabel.CENTER);
        lblAbsConMod.setLocation(442, 182);
        lblAbsConMod.setSize(emptyDim);
        mainPanel.add(lblAbsConMod);

        lblAbsIntMod = new JLabel("0", JLabel.CENTER);
        lblAbsIntMod.setLocation(442, 222);
        lblAbsIntMod.setSize(emptyDim);
        mainPanel.add(lblAbsIntMod);

        lblAbsWisMod = new JLabel("0", JLabel.CENTER);
        lblAbsWisMod.setLocation(442, 262);
        lblAbsWisMod.setSize(emptyDim);
        mainPanel.add(lblAbsWisMod);

        lblAbsChaMod = new JLabel("0", JLabel.CENTER);
        lblAbsChaMod.setLocation(442, 302);
        lblAbsChaMod.setSize(emptyDim);
        mainPanel.add(lblAbsChaMod);

        lblAbsStrRaceMod = new JLabel("0", JLabel.CENTER);
        lblAbsStrRaceMod.setLocation(192, 102);
        lblAbsStrRaceMod.setSize(emptyDim);
        mainPanel.add(lblAbsStrRaceMod);

        lblAbsDexRaceMod = new JLabel("0", JLabel.CENTER);
        lblAbsDexRaceMod.setLocation(192, 142);
        lblAbsDexRaceMod.setSize(emptyDim);
        mainPanel.add(lblAbsDexRaceMod);

        lblAbsConRaceMod = new JLabel("0", JLabel.CENTER);
        lblAbsConRaceMod.setLocation(192, 182);
        lblAbsConRaceMod.setSize(emptyDim);
        mainPanel.add(lblAbsConRaceMod);

        lblAbsIntRaceMod = new JLabel("0", JLabel.CENTER);
        lblAbsIntRaceMod.setLocation(192, 222);
        lblAbsIntRaceMod.setSize(emptyDim);
        mainPanel.add(lblAbsIntRaceMod);

        lblAbsWisRaceMod = new JLabel("0", JLabel.CENTER);
        lblAbsWisRaceMod.setLocation(192, 262);
        lblAbsWisRaceMod.setSize(emptyDim);
        mainPanel.add(lblAbsWisRaceMod);

        lblAbsChaRaceMod = new JLabel("0", JLabel.CENTER);
        lblAbsChaRaceMod.setLocation(192, 302);
        lblAbsChaRaceMod.setSize(emptyDim);
        mainPanel.add(lblAbsChaRaceMod);
    }

    private void initCboBoxes(){
        String[] races = {"Dwarf - Hill", "Dwarf - Moutain", "Elf - High", "Elf - Wood", "Halfling - Lightfoot",
                "Halfling - Stout", "Human", "Dragonborn", "Gnome - Forest", "Gnome - Rock", "Half-Elf", "Half-Orc",
                "Tiefling"};
        DefaultComboBoxModel<String> raceModel = new DefaultComboBoxModel<>(races);
        cboRace = new JComboBox<>();
        cboRace.setModel(raceModel);
        cboRace.setLocation(116, 383);
        cboRace.setSize(180, 28);
        cboRace.addActionListener(actionEvent -> updateRaceMods((String)cboRace.getSelectedItem()));
        mainPanel.add(cboRace);

        String[] rollTypes = {"3d6", "2d6+6", "4d6 drop lowest 1", "5d6 drop lowest 2"};
        DefaultComboBoxModel<String> rollModel = new DefaultComboBoxModel<>(rollTypes);
        cboRollType = new JComboBox<>();
        cboRollType.setModel(rollModel);
        cboRollType.setLocation(207, 498);
        cboRollType.setSize(108, 28);
        mainPanel.add(cboRollType);
    }

    private void initButtons(){
        JButton btnRollAbs = new JButton("Roll!");
        btnRollAbs.setLocation(218, 425);
        btnRollAbs.setSize(160, 65);
        btnRollAbs.addActionListener(actionEvent -> rollStats());
        mainPanel.add(btnRollAbs);

        JButton btnDone = new JButton("Done");
        btnDone.setLocation(233, 553);
        btnDone.setSize(126, 62);
        mainPanel.add(btnDone);
    }

    private void initChkBoxes(){
        Dimension boxDim = new Dimension(22, 21);

        chkStrRaceMod = new JCheckBox();
        chkStrRaceMod.setLocation(508, 102);
        chkStrRaceMod.setSize(boxDim);
        chkStrRaceMod.setEnabled(false);
        chkStrRaceMod.addActionListener(actionEvent -> handleCheckBoxes(chkStrRaceMod));
        mainPanel.add(chkStrRaceMod);

        chkDexRaceMod = new JCheckBox();
        chkDexRaceMod.setLocation(508, 142);
        chkDexRaceMod.setSize(boxDim);
        chkDexRaceMod.setEnabled(false);
        chkDexRaceMod.addActionListener(actionEvent -> handleCheckBoxes(chkDexRaceMod));
        mainPanel.add(chkDexRaceMod);

        chkConRaceMod = new JCheckBox();
        chkConRaceMod.setLocation(508, 182);
        chkConRaceMod.setSize(boxDim);
        chkConRaceMod.setEnabled(false);
        chkConRaceMod.addActionListener(actionEvent -> handleCheckBoxes(chkConRaceMod));
        mainPanel.add(chkConRaceMod);

        chkIntRaceMod = new JCheckBox();
        chkIntRaceMod.setLocation(508, 222);
        chkIntRaceMod.setSize(boxDim);
        chkIntRaceMod.setEnabled(false);
        chkIntRaceMod.addActionListener(actionEvent -> handleCheckBoxes(chkIntRaceMod));
        mainPanel.add(chkIntRaceMod);

        chkWisRaceMod = new JCheckBox();
        chkWisRaceMod.setLocation(508, 262);
        chkWisRaceMod.setSize(boxDim);
        chkWisRaceMod.setEnabled(false);
        chkWisRaceMod.addActionListener(actionEvent -> handleCheckBoxes(chkWisRaceMod));
        mainPanel.add(chkWisRaceMod);
    }

    private void updateRaceMods(String race){
        resetRaceModLabels();
        resetCheckBoxes();

        switch (race) {
            case "Dwarf - Hill":
                lblAbsConRaceMod.setText("2");
                lblAbsWisRaceMod.setText("1");
                break;
            case "Dwarf - Moutain":
                lblAbsStrRaceMod.setText("2");
                lblAbsConRaceMod.setText("2");
                break;
            case "Elf - High":
                lblAbsDexRaceMod.setText("2");
                lblAbsIntRaceMod.setText("1");
                break;
            case "Elf - Wood":
                lblAbsDexRaceMod.setText("2");
                lblAbsWisRaceMod.setText("1");
                break;
            case "Halfling - Lightfoot":
                lblAbsDexRaceMod.setText("2");
                lblAbsWisRaceMod.setText("1");
                break;
            case "Halfling - Stout":
                lblAbsDexRaceMod.setText("2");
                lblAbsConRaceMod.setText("1");
                break;
            case "Human":
                lblAbsStrRaceMod.setText("1");
                lblAbsDexRaceMod.setText("1");
                lblAbsConRaceMod.setText("1");
                lblAbsIntRaceMod.setText("1");
                lblAbsWisRaceMod.setText("1");
                lblAbsChaRaceMod.setText("1");
                break;
            case "Dragonborn":
                lblAbsStrRaceMod.setText("2");
                lblAbsChaRaceMod.setText("1");
                break;
            case "Gnome - Forest":
                lblAbsDexRaceMod.setText("1");
                lblAbsIntRaceMod.setText("2");
                break;
            case "Gnome - Rock":
                lblAbsConRaceMod.setText("1");
                lblAbsIntRaceMod.setText("2");
                break;
            case "Half-Elf":
                lblAbsChaRaceMod.setText("2");
                enableCheckBoxes();
                break;
            case "Half-Orc":
                lblAbsStrRaceMod.setText("2");
                lblAbsConRaceMod.setText("1");
                break;
            case "Tiefling":
                lblAbsIntRaceMod.setText("1");
                lblAbsChaRaceMod.setText("2");
                break;
                default:
                    resetRaceModLabels();
                    resetCheckBoxes();
                    break;
        }
    }

    private void resetRaceModLabels(){
        lblAbsStrRaceMod.setText("0");
        lblAbsDexRaceMod.setText("0");
        lblAbsConRaceMod.setText("0");
        lblAbsIntRaceMod.setText("0");
        lblAbsWisRaceMod.setText("0");
        lblAbsChaRaceMod.setText("0");
    }

    private void resetCheckBoxes(){
        chkStrRaceMod.setEnabled(false);
        chkDexRaceMod.setEnabled(false);
        chkConRaceMod.setEnabled(false);
        chkIntRaceMod.setEnabled(false);
        chkWisRaceMod.setEnabled(false);

        chkStrRaceMod.setSelected(false);
        chkDexRaceMod.setSelected(false);
        chkConRaceMod.setSelected(false);
        chkIntRaceMod.setSelected(false);
        chkWisRaceMod.setSelected(false);
    }

    private void enableCheckBoxes(){
        chkStrRaceMod.setEnabled(true);
        chkDexRaceMod.setEnabled(true);
        chkConRaceMod.setEnabled(true);
        chkIntRaceMod.setEnabled(true);
        chkWisRaceMod.setEnabled(true);
    }

    private void handleCheckBoxes(JCheckBox box){
        if (box.isSelected()){
            halfElfMod--;
            if (box == chkStrRaceMod){
                lblAbsStrRaceMod.setText("1");
            }
            else if (box == chkDexRaceMod){
                lblAbsDexRaceMod.setText("1");
            }
            else if (box == chkConRaceMod){
                lblAbsConRaceMod.setText("1");
            }
            else if (box == chkIntRaceMod){
                lblAbsIntRaceMod.setText("1");
            }
            else if (box == chkWisRaceMod){
                lblAbsWisRaceMod.setText("1");
            }
        }
        else {
            halfElfMod++;
            if (box == chkStrRaceMod){
                lblAbsStrRaceMod.setText("0");
            }
            else if (box == chkDexRaceMod){
                lblAbsDexRaceMod.setText("0");
            }
            else if (box == chkConRaceMod){
                lblAbsConRaceMod.setText("0");
            }
            else if (box == chkIntRaceMod){
                lblAbsIntRaceMod.setText("0");
            }
            else if (box == chkWisRaceMod){
                lblAbsWisRaceMod.setText("0");
            }
        }

        if (halfElfMod < 0){
            box.setSelected(false);
        }

        updateStats();
    }

    private void updateStats(){
        lblAbsStr.setText(Integer.toString(str + Integer.parseInt(lblAbsStrRaceMod.getText())));
        lblAbsDex.setText(Integer.toString(dex + Integer.parseInt(lblAbsDexRaceMod.getText())));
        lblAbsCon.setText(Integer.toString(con + Integer.parseInt(lblAbsConRaceMod.getText())));
        lblAbsInt.setText(Integer.toString(intel + Integer.parseInt(lblAbsIntRaceMod.getText())));
        lblAbsWis.setText(Integer.toString(wis + Integer.parseInt(lblAbsWisRaceMod.getText())));
        lblAbsCha.setText(Integer.toString(cha + Integer.parseInt(lblAbsChaRaceMod.getText())));

        lblAbsStrMod.setText(Integer.toString((int)Math.floor((Double.parseDouble(lblAbsStr.getText()) - 10.0d) / 2.0d)));
        lblAbsDexMod.setText(Integer.toString((int)Math.floor((Double.parseDouble(lblAbsDex.getText()) - 10.0d) / 2.0d)));
        lblAbsConMod.setText(Integer.toString((int)Math.floor((Double.parseDouble(lblAbsCon.getText()) - 10.0d) / 2.0d)));
        lblAbsIntMod.setText(Integer.toString((int)Math.floor((Double.parseDouble(lblAbsInt.getText()) - 10.0d) / 2.0d)));
        lblAbsWisMod.setText(Integer.toString((int)Math.floor((Double.parseDouble(lblAbsWis.getText()) - 10.0d) / 2.0d)));
        lblAbsChaMod.setText(Integer.toString((int)Math.floor((Double.parseDouble(lblAbsCha.getText()) - 10.0d) / 2.0d)));
    }

    private void rollStats(){
        if (cboRollType.getSelectedItem().equals("3d6")){
            str = RandomNumberGenerator.NumberBetween(3, 18);
            dex = RandomNumberGenerator.NumberBetween(3, 18);
            con = RandomNumberGenerator.NumberBetween(3, 18);
            intel = RandomNumberGenerator.NumberBetween(3, 18);
            wis = RandomNumberGenerator.NumberBetween(3, 18);
            cha = RandomNumberGenerator.NumberBetween(3, 18);
        }
        else if (cboRollType.getSelectedItem().equals("2d6+6")){
            str = RandomNumberGenerator.NumberBetween(2, 12) + 6;
            dex = RandomNumberGenerator.NumberBetween(2, 12) + 6;
            con = RandomNumberGenerator.NumberBetween(2, 12) + 6;
            intel = RandomNumberGenerator.NumberBetween(2, 12) + 6;
            wis = RandomNumberGenerator.NumberBetween(2, 12) + 6;
            cha = RandomNumberGenerator.NumberBetween(2, 12) + 6;
        }
        else if (cboRollType.getSelectedItem().equals("4d6 drop lowest 1")){
            List<List<Integer>> rollList = new ArrayList<>();

            for (int i = 0; i < 6; i++){
                rollList.add(new ArrayList<>());
                for (int j = 0; j < 4; j++){
                    rollList.get(i).add(RandomNumberGenerator.NumberBetween(1, 6));
                }
            }

            for (List<Integer> innerList : rollList){
                innerList.sort(Collections.reverseOrder());
            }

            assignRolls(rollList);
        }
        else if (cboRollType.getSelectedItem().equals("5d6 drop lowest 2")){
            List<List<Integer>> rollList = new ArrayList<>();

            for (int i = 0; i < 6; i++){
                rollList.add(new ArrayList<>());
                for (int j = 0; j < 5; j++){
                    rollList.get(i).add(RandomNumberGenerator.NumberBetween(1, 6));
                }
            }

            for (List<Integer> innerList : rollList){
                innerList.sort(Collections.reverseOrder());
            }

            assignRolls(rollList);
        }

        updateStats();
    }

    private void assignRolls(List<List<Integer>> rollList){
        str = rollList.get(0).get(0) + rollList.get(0).get(1) + rollList.get(0).get(2);
        dex = rollList.get(1).get(0) + rollList.get(1).get(1) + rollList.get(1).get(2);
        con = rollList.get(2).get(0) + rollList.get(2).get(1) + rollList.get(2).get(2);
        intel = rollList.get(3).get(0) + rollList.get(3).get(1) + rollList.get(3).get(2);
        wis = rollList.get(4).get(0) + rollList.get(4).get(1) + rollList.get(4).get(2);
        cha = rollList.get(5).get(0) + rollList.get(5).get(1) + rollList.get(5).get(2);
    }
}