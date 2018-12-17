package com.tensionup.seoul_story.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.snatik.storage.Storage;
import com.tensionup.seoul_story.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class FileManagerUtil {
    private static final String DIR_NAME = "user_data";

    private static final String FAVORITE_CARD_XML_NAME = "favorite.xml";

    private static final String FAVORITE_CARD_XML_ELEM_ROOT = "favorites";
    private static final String FAVORITE_CARD_XML_ELEM_CATEGORY = "category";

    private static final String TAG = FileManagerUtil.class.getSimpleName() + "/DEV";


    private static HashMap<Integer, Bitmap> m_categoryImages = new HashMap<Integer, Bitmap>();
    private static ArrayList<String> m_favoriteCategory = new ArrayList<>();


    public static void setFavoriteCategory(ArrayList<String> favoriteCategory) {
        FileManagerUtil.m_favoriteCategory = favoriteCategory;
    }

    public static ArrayList<String> getFavoriteCategory() {
        return m_favoriteCategory;
    }

    public static void writeXmlCategoryState(Context context, List<String> categoryList) throws IOException {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Storage internalStorage = new Storage(context);

            String path = internalStorage.getInternalFilesDirectory();
            String dir = path + File.separator + DIR_NAME;
            String xmlFilePath =  dir + File.separator + FAVORITE_CARD_XML_NAME;

            boolean fileExists = internalStorage.isFileExist(xmlFilePath);

            Document doc = docBuilder.newDocument();

            Element rootElement;
//            if( fileExists ) {
//                doc = docBuilder.parse(new File(xmlFilePath));
//                rootElement = (Element) doc.getDocumentElement();
//            } else {
//                rootElement = doc.createElement(EMERGENCY_CONTACTS_XML_ELEM_ROOT);
//            }

            rootElement = doc.createElement(FAVORITE_CARD_XML_ELEM_ROOT);

            /* Make elements start */


            for (String data :
                    categoryList) {
                Element contactElement = doc.createElement(FAVORITE_CARD_XML_ELEM_CATEGORY);
                /* Make elements end */

                /* Define attributes start */
                contactElement.setTextContent(data);

                rootElement.appendChild(contactElement);
            }

//            if( !fileExists ) {
//                doc.appendChild(rootElement);
//            }
            doc.appendChild(rootElement);

            // XML 파일로 쓰기
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            final boolean dirExists = internalStorage.isDirectoryExists(dir);

            if( !dirExists ) {
                internalStorage.createDirectory(dir);
            }

            StreamResult result = new StreamResult(new FileOutputStream(new File(xmlFilePath), false));

            transformer.transform(source, result);
            Log.d(TAG, "writeXmlFavoriteCards: \n" + source.getNode().getTextContent());


        }
        catch (ParserConfigurationException | TransformerException pce)
        {
            pce.printStackTrace();
        }
    }

    public static List<String> readXmlCategoryState(Context context) throws IOException {
        List<String> contactItems = null;
        try {
            contactItems = new ArrayList<>();
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Storage internalStorage = new Storage(context);

            String path = internalStorage.getInternalFilesDirectory();
            String dir = path + File.separator + DIR_NAME;
            String xmlFilePath = dir + File.separator + FAVORITE_CARD_XML_NAME;

            boolean fileExists = internalStorage.isFileExist(xmlFilePath);

            Document doc = docBuilder.newDocument();

            if (fileExists) {
                doc = docBuilder.parse(new File(xmlFilePath));
            } else {
                return null;
            }

            NodeList contacts = doc.getElementsByTagName(FAVORITE_CARD_XML_ELEM_CATEGORY);

            String name;
            String phoneNumber;

            for (int i = 0; i < contacts.getLength(); i++) {
                Node card = contacts.item(i);

                name = card.getTextContent();

                contactItems.add(name);
            }

        } catch (ParserConfigurationException | SAXException pce) {
            pce.printStackTrace();
        }

        return contactItems;
    }

    public static boolean IsExistCategoryState(Context context) {
        Storage internalStorage = new Storage(context);

        String path = internalStorage.getInternalFilesDirectory();
        String dir = path + File.separator + DIR_NAME;
        String xmlFilePath =  dir + File.separator + FAVORITE_CARD_XML_NAME;

        boolean fileExists = internalStorage.isFileExist(xmlFilePath);

        return fileExists;
    }

    public static boolean LoadFavoriteCardBackgroundImages(Resources res) {

        int[] resIdImgs = {
                R.drawable.category_gov,
                R.drawable.category_infra,
                R.drawable.category_sculture,
                R.drawable.category_env,
                R.drawable.category_economy,
                R.drawable.category_health,
                R.drawable.category_citybuild,
                R.drawable.category_safe,
                R.drawable.category_finance,
                R.drawable.category_traffic,
                R.drawable.category_welfare,
                R.drawable.category_woman
        };

        for(int i=0; i<resIdImgs.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(res, resIdImgs[i]);
            m_categoryImages.put(resIdImgs[i], bitmap);
        }

        return true;
    }

    public static Bitmap getCategoryBitmap(int resID) {
        return m_categoryImages.get(resID);
    }
}
