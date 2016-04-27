package com.falconpack.android;

import java.util.Locale;

import com.falconpack.android.common.CommonUtility;
import com.falconpack.android.common.Constants;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductDetailsActivity extends Activity {




	ProductsAdapter mProductsAdapter;
	Context mContext;
	Activity activity;
	GridView gridView;
	String[] list;
	String path = "", title = "";
	int pos = 0, catPos = -1;
	// ArrayList<String[]> arrayListImages, arrayListDesc;
	String[] arrayImages, arrayDescs;
	private ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_productdetailslist);

		mContext = ProductDetailsActivity.this;
		activity = ProductDetailsActivity.this;

		pDialog = new ProgressDialog(activity);
		pDialog.setMessage("Retrieving Images...");
		pDialog.setCancelable(false);
		pDialog.show();

		path = getIntent().getStringExtra("path");
		catPos = getIntent().getIntExtra("catId", -1);
		pos = getIntent().getIntExtra("pos", 0);
		title = getIntent().getStringExtra("Title");

	
		gridView = (GridView) findViewById(R.id.gridview);
		if (catPos == 0) {

			if (pos == 0) {

				String[] Aluminiumfoilimages = { "malaf008", "malaf011",
						"malaf013", "malaf014", "malaf005", "malaf006",
						"malaf319", "malaf320", "malaf322", "malaf323",
						"tplpb037", "tplpb036" };
				String[] AluminiumFoil = { "\n25sqtf(20cm) \n1X24",
						"\n37.5sqft(45cm) \n1X24", "\n37.5sqft(45cm)",
						"\n75sqtf(30cm) \n1X24", "\n150sqtf(45cm) \n1X12",
						"\n200sqtf(30cm) \n1X12", "\n1.8kgX30cm \n1X6",
						"\n2.5kgX45cm \n1X6", "\n3.5kgX30cm \n1X6",
						"\n5.2kgX45cm \n1X6", "\n35X27.3cm \n24X100",
						"\n35X27.3cm \n12X200" };

				arrayImages = Aluminiumfoilimages;
				arrayDescs = AluminiumFoil;

				Aluminiumfoilimages = null;
				AluminiumFoil = null;
				// arrayListImages.add(Aluminiumfoilimages);
				// arrayListDesc.add(AluminiumFoil);
			} else if (pos == 1) {
				String[] Foilwrapimages = { "malfw015", "malfw012", "malfw013",
						"malfw009", "malfw001", "malfw014" };
				String[] FoilWrap = { "\n25X35 \n4X500", "\n25X35 \n4X500",
						"\n25X35 \n4X500", "\n25X35 \n4X500",
						"\n25X35 \n4X500", "\n25X35 \n4X500" };
				// arrayListImages.add(Foilwrapimages);
				// arrayListDesc.add(FoilWrap);

				arrayImages = Foilwrapimages;
				arrayDescs = FoilWrap;

				Foilwrapimages = null;
				FoilWrap = null;

			} else if (pos == 2) {

				String[] Clingfilmimages = { "mclfm004", "mclfm025",
						"mclfm006", "mclfm039", "mclfm040", "mclfm035",
						"mclfm036", "mclfm001", "tclfm045", "tclfm046",
						"tclfm044", "tclfm043", "tclfm040", "tclfm041",
						"tclfm042" };
				String[] ClingFilm = { "\n100sqtf(30cm) \n1X24",
						"\n100sqtf(45cm) \n1X24", "\n200sqtf(30cm) \n1X24",
						"\n1.3kgX30cm \n1X6", "2kgX45cm\n 1X6",
						"1000gmX30cm\n 1X6", "1500X45cm\n 1X6",
						"2kg(45cm)\n 1X6", "1000mX30cm\n 1X1pc",
						"1500mX40cm\n 1X1pc", "1500mX45cm\n 1X1pc",
						"7kg(45cm)\n 1X1", "111(6kg)\n 45cm\n 1X1pc",
						"222(7.8kg)\n 45cm\n 1X1pc", "333(kg)\n 45cm\n 1X1pc" };
				// arrayListImages.add(Clingfilmimages);
				// arrayListDesc.add(ClingFilm);

				arrayImages = Clingfilmimages;
				arrayDescs = ClingFilm;

				Clingfilmimages = null;
				ClingFilm = null;

			}

		} else if (catPos == 1) {

			// arrayListImages.add(Clearcontainersimages);
			// arrayListImages.add(cakecontainerimages);
			// arrayListImages.add(delicontainerimages);
			// arrayListImages.add(saladcontainerimages);
			// arrayListImages.add(microwavecontainerimages);

			// arrayListDesc.add(ClearContainer);
			// arrayListDesc.add(CakeContainer);
			// arrayListDesc.add(DeliContainer);
			// arrayListDesc.add(SaladContainer);
			// arrayListDesc.add(MicrowaveContainer);

			if (pos == 0) {

				String[] AluminiumContainer = {
						"8325\n260\n130X101 \n93X66\n43 \n1X1000",
						"8342/128\n430\n147X122 \n107X83 \n50\n1X1000",
						"8357/155 \n580\n161X1161 \n117X117 \n42 \n1X1000",
						"8368 \n205X127 \n151X71 \n55 \n1X900",
						"8367/208 \n670\n205X115 \n160X67 \n57 \n1X500",
						"8379 \n765\n215X140 \n174X104 \n4 \n1X500",
						"8389 \n830\n212X151 \n171X105 \n49 \n1X1000",
						"83120 \n1250\n141X191 \n197X147 \n44 \n1X400",
						"83185/204 \n1900\n263X193 \n220X149 \n57 \n1X400",
						"83190/102 \n2000\n312X213 \n276X176 \n46 \n1X250",
						"83241/245 \n2330\n246X246 \n202X202 \n59 \n1X200",
						"73365/131 \n3200\n331X258 \n256X206 \n57 \n1X100",
						"501/14082 \n110 \n78 \n55 \n40 \n1X1500",
						"527/1232 \n155 \n106 \n85 \n21 \n1X3750",
						"520 \n160 \n88 \n57.3 \n42.2 \n1X2760",
						"542 \n135 \n123 \n65 \n20 \n1X1200",
						"548 \n600 \n210 \n180 \n24 \n1X960",
						"526 \n635 \n200 \n170 \n20 \n1X1080",
						"533/3180 \n715 \n182 \n144 \n40 \n1X500",
						"430 \n715 \n182 \n144 \n20 \n1X1000",
						"546 \n830 \n202 \n165 \n58 \n1X750",
						"552/31135 \n1380 \225 \n195 \n42 \n1X300",
						"515 \n980 \n250 \n225 \n21 \n1X550",
						"532 \n1240 \n227 \n249 \n23 \n1X480",
						"7314 \n140 \n10X60 \n74X37 \n35 \n1X1000",
						"8384/207/403 \n1400 \n220X158 \n180X117 \n45 \n1X500",
						"161 \n880 \n238X142 \n207X107 \n36 \n1X810",
						"224 \n1170 \n263X121 \n224X121 \n40 \n1X480",
						"83163 \n1550 \n25.1X15.1 \n20.5X10.5 \n60 \n1X800",
						"221 \n1820 \n315X170 \n280X125 \n40 \n1X600",
						"187 \n610 \n225X127 \n185X92 \n40 \n1X1000",
						"325/8582 \n825 \n230X180 \n199X150 \n40 \n1X500",
						"8576 \n825 \n230X180 \n199X150 \n40 \n1X500",
						"53885 \n9650 \n527X325 \n450X250 \n77 \n1X50",
						"53535 \n7120 \n460X337 \n370X250 \n70 \n1X50",
						"53730 \n7200 \n472X348 \n385X254 \n86 \n1X50",
						"6550-V0 \n350 \n263X180 \n187X110 \n17 \n1X150",
						"6586-V1 \n1000 \n360X245 \n255X155 \n26 \n1X100",
						"65180-V2 \n1000 \n427X285 \n320X185 \n30 \n1X50",
						"65220-V3 \n1550 \n251X151 \n205X105 \n68 \n1X800" };

				String[] Aluminiumcontainerimages = { "malfc017-018",
						"malfc009-010", "malfc021-022", "malfc025-026",
						"malfc027-028", "malfc042-031", "malfc007-008",
						"malfc003-004", "malfc005-006", "malfc032-033",
						"malfc013-016", "malfc001-002", "malfc065", "talac109",
						"talcc006", "talac093", "talac089", "talac110",
						"malfc061", "malfc085", "talac088", "talac091",
						"talac081", "talac084", "talac071", "talac125",
						"talac060", "talac042", "malfc079", "talac133",
						"talac132", "malfc059-060", "malfc057-058", "malfc054",
						"malfc055", "malfc056", "malfc029", "malfc011",
						"malfc012", "talap0006" };

				// arrayListImages.add(Aluminiumcontainerimages);
				// arrayListDesc.add(AluminiumContainer);
				// AluminiumContainer = Aluminiumcontainerimages;

				arrayImages = Aluminiumcontainerimages;
				arrayDescs = AluminiumContainer;

				Aluminiumcontainerimages = null;
				AluminiumContainer = null;

			} else if (pos == 1) {

				String[] ClearContainer = { "18 \n1X300", "24 \n1X300",
						"32 \n1X300", "48 \n1X300", "250 \n1X600",
						"375 \n1X600", "500 \n1X600", "750 \n1X400",
						"1000 \n1X400", "150 \n171.5X197X69 \n1X600",
						"250 \n232.2X189X66 \n1X500",
						"375 \n232.2X189X84 \n1X500",
						"500 \n165X266.5X52 \n1X500",
						"750 \n187X305.5X58 \n1X400",
						"1000 \n203X160X65 \n1X400",
						"8 \n155.6X128X41 \n1X250",
						"12 \n155.6X128X50 \n1X250",
						"16 \n155.6X128X54 \n1X250",
						"24 \n171.5X197X45 \n1X250",
						"32 \n171.5X197X60 \n1X250",
						"48 \n232.2X189X66 \n1X250",
						"64 \n232.2X189X84 \n1X250",
						"64 \n232.2X189X84 \n1X500", "550 \n204X80X34 \n1X450",
						"850 \n204X80X57  \n1X450",
						"1200 \n215X101X81  \n1X350",
						"1400 \n183X159X52  \n1X300",
						"370 \n102X95X55  \n1X600",
						"500 \n117X100X55  \n1X600",
						"600 \n160X115X40  \n1X400",
						"750 \n100X115X50  \n1X400",
						"1000 \n100X115X65  \n1X400",
						"250 \n107X78X35  \n1X600", "375 \n118X87X40  \n1X400",
						"500 \n130X98X40  \n1X450",
						"750 \n145X118X55  \n1X300",
						"1000 \n160X130X60  \n1X200", "163X116X65  \n1X800",
						"205X110X77  \n1X600", "163X115X75  \n1X800",
						"225X125X80  \n1X600", "252X125X90  \n1X500",
						"180X180X50  \n1X240", "180X180X100  \n1X180",
						"215X170X123  \n1X250", "230X195X153  \n1X250",
						"93X65  \n1X200", "375 /n1X400", "500 /n1X400",
						"750 /n1X300", "1000 /n1X300", "250 \n1X990",
						"500 \n1X780", "750 \n1X440", "1000 \n1X630",
						"190X119X60 \n1X400",
						"KT5 Small \n282.5X141.2X48 \n1X500",
						"KT6 Medium \n312X159X53.5 \n1X500",
						"KT8 Large \n386X220X48 \n1X250",
						"V00490 \n165X45 \n1X300",
						"cont.V497 \n1000 \n150X145X50 \n 1X300",
						"V499 \n2000 \n187X184X63 \n1X140",
						"V495 \n500 \n115X110X50 \n1X600",
						"V496 \n750 \n150X145X35 \n1X300",
						"V501 \n700 \n192X167X80 \n1X250",
						"V500 \n1000 \n192X167X85 \n1X250",
						"V520 \n195X145X20 \n1X210",
						"V521 \n195X145X30 \n1X200",
						"V522 \n195X145X45 \n1X210",
						"1711 \n180X90X50 \n1X300", "1712 \n180X90X75 \n1X300",
						"1713 \n180X90X90 \n1X300",
						"1714 \n180X90X100 \n1X300",
						"1773 \n320X120X100 \n1X240", "0.75 \n45X31 \n1X5000",
						"1 \n45X31 \n1X5000", "2 \n62X29 \n20X125",
						"3 \n75X34 \n1X2500", "4 \n75X47 \n1X2500",
						"80 \n70X41 \n1X2000", "35 \n1X2000 ", "75 \n1X1500",
						"100 \n1X1500", "Clear \n1X1000", "1 \n1X5000",
						"2 \n1X2500", "3 \n1X2500", "4 \n1X2500",
						"30 \n1X2000", "60 \n1X1000", "80 \n1X1000",
						"90 \n1X1000", "TPLLI077/186 \n1[PP] \n50X100",
						"TPLLI040/187 \n2[PP] \n25X100",
						"TPLLI040/187 \n2.5[PP] \n25X100",
						"TPLLI040/188 \n3[PP] \n25X100",
						"TPLLI040/189 \n4[PP] \n25X100" };
				String[] Clearcontainersimages = { "TPLBW020", "TPLBW021",
						"TPLBW022", "TPLBW023", "TPLCH186", "TPLCH187",
						"TPLCH188", "TPLCH189", "TPLCH190", "TPLCH011",
						"TPLCH011", "TPLCH013", "TPLCH014", "TPLCH015",
						"TPLCH010", "TPLCC014", "TPLCC008", "TPLCC009",
						"TPLCC010", "TPLCC011", "TPLCC012", "TPLCC013",
						"TPLCC085", "TPLCH018", "TPLCH017", "TPLCH019",
						"TPLCH020", "TPLCH029", "TPLCH030", "TPLCH031",
						"TPLCH032", "TPLCH028", "TPLCH040", "TPLCH041",
						"TPLCH042", "TPLCH043", "TPLCH039", "TPLCC023",
						"TPLCC024", "TPLCC025", "TPLCC026", "TPLCC027",
						"TPLCH066", "TPLCH050", "TPLBK039", "TPLBK040",
						"TPLCH044", "TPLCH182", "TPLCH183", "TPLCH184",
						"TPLCH185", "TPLCH062", "TPLCH063", "TPLCH064",
						"TPLCH065", "TPLCH058", "TPLBK041", "TPLBK042",
						"TPLBK043", "TPLPC026-W-L-TPLL109", "TPLCH024",
						"TPLCH025", "TPLCH022", "TPLCH023", "TPLCH027",
						"TPLCH026", "TPLCH005", "TPLCH006", "TPLCH007",
						"TPLCH133", "TPLCH134", "TPLCH135", "TPLCH136",
						"TPLCH137", "TPLCC103", "TPLCC100", "TPLCC101",
						"TPLCC081", "TPLCC082", "TPLCC034", "TPLPC008",
						"TPLPC009", "TPLPC007", "TPLCU035", "TPLCC114",
						"TPLCC115", "TPLCC116", "TPLCC117", "TPLCH178",
						"TPLCH179", "TPLCH180", "TPLCH181", "TPLCC120",
						"TPLCC121", "TPLCC122", "TPLCC123", "TPLCC124" };
				// arrayListImages.add(Clearcontainersimages);
				// arrayListDesc.add(ClearContainer);

				arrayImages = Clearcontainersimages;
				arrayDescs = ClearContainer;

				Clearcontainersimages = null;
				ClearContainer = null;

			} else if (pos == 2) {
				String[] CakeContainer = { "240 \n1X60", "280 \n1X60",
						"H022/G/T 7\" \n1X60", "H022/G/T 10\" \n1X80",
						"LID-TPLLI161 \n180MM \n85 \n1X240",
						"LID-TPLLI165 \n260MM \n80 \n1X100",
						"LID-TPLLI166 \n180MM \n100 \n1X240",
						"LID-TPLLI167 \n295MM \n100 \n1X120",
						"LID-TPLLI163 \n220MM \n80 \n1X150",
						"LID-TPLLI164 \n220MM \n100 \n1X150",
						"LID-TPLLI165 \n260MM \n80 \n1X100",
						"LID-TPLLI166 \n260MM \n100 \n1X100",
						"LID-TPLLI167 \n295MM \n100 \n1X120",
						"LID-TPLLI161 \n180MM \n85 \n1X240",
						"H040 \n7\" \n1X100", "H041  \n8\" \n1X100",
						"KT251 \n 318X286X100 \n1X200",
						"KT252 \n 318X286X70 \n1X100",
						"P1002 \n305X74 \n1X100", "P1001 \n305X104 \n1X100",
						"180X180X65 \n1X160", "180X180X80 \n1X150",
						"180X180X100 \n1X140", "220X220X80 \n1X210",
						"220X220X100 \n1X190", "250X250X80 \n1X140",
						"250X250X100 \n1X130", "280X280X80 \n1X150",
						"280X280X100 \n1X130", "155X115X50 \n1X480",
						"155X115X80 \n1X480", "180X90X50 \n1X350",
						"180X90X75 \n1X340", "180X90X90 \n1X330",
						"180X160X50 \n1X240", "180X160X90 \n1X220",
						"180X160X110 \n1X220", "4 CUP \n1X200",
						"6 CUP \n1X200", "12 CUP \n1X100", "24 CUP \n1X50",
						"235X80 \n1X240", "235X100 \n1X210", "245X80 \n1X150",
						"245X100 \n1X140", "295X80 \n1X175", "295X100 \n1X160" };
				String[] cakecontainerimages = { "TPPCX019", "TPPCX032",
						"TPLBK004", "TPLBK001", "TPLBK091",
						"TPLBK094-LID-TPLL165", "TPLBK094-LID-TPLL166",
						"TPLBK096", "TPLBK092-LID-TPLL163",
						"TPLBK092-LID-TPLL164", "TPLBK093-LID-TPLL165",
						"TPLBK093-LID-TPLL166", "TPLBK095", "TPLLI162",
						"TPLBK005", "TPLBK006", "TPLBK033", "TPLBK032",
						"TPLBK031", "TPLBK034", "TPLBK102", "TPLBK103",
						"TPLBK104", "TPLBK105", "TPLBK106", "TPLBK107",
						"TPLBK108", "TPLBK109", "TPLBK110", "TPLBK111",
						"TPLBK112", "TPLBK113", "TPLBK114", "TPLBK115",
						"TPLBK116", "TPLBK117", "TPLBK118", "TPLCH171",
						"TPLCH172", "TPLCH173", "TPLCH174", "TPLCH142",
						"TPLCH143", "TPLCH144", "TPLCH145", "TPLCH146",
						"TPLCH147" };

				// arrayListImages.add(cakecontainerimages);
				// arrayListDesc.add(CakeContainer);

				arrayImages = cakecontainerimages;
				arrayDescs = CakeContainer;

				cakecontainerimages = null;
				CakeContainer = null;

			} else if (pos == 3) {

				String[] DeliContainer = {
						"H036/G/T-PP \n800 \n210X174X50 \n1X300",
						"H037/G/T-PP \n900 \n225X153X50 \n1X300",
						"H037/G-H050 \n1000 \n225X153X50 \n1X255",
						"H037/G/TP \n900 \n225X154X50 \n1X200",
						"H057/G/TP \n900 \n225X154X50 \n1X200",
						"H035/G/T-PP \n650 \n174X164X49 \n1X300",
						"3 SEC BLK/RED. W/L/D \n(H107GT) \n \n \n1X150",
						"H077/G/T  \n257X195X73 \n1X80", "H045/G/T  \n1X150",
						"H048/G/T  \n1X400", "H069/G/T  \n1X500",
						"YP-0.6/ST-2/A-06 \n217X90X45 \n1X500",
						"YP-0.8/ST-3 \n165X114X45 \n1X500",
						"YP-1.0/ST-4 \n186X127X45 \n1X500",
						"YP-1.5/ST-5/SCF-03 \n216X135X45 \n1X500",
						"YP-2.5/A-08 \n266X198X50 \n1X200",
						"CF-S6-B \n178X133X22 \n1X800", "SCF-01 \n \n1X500",
						"SCF-02 \n \n1X500", "SCF-03 \n \n1X500",
						"SCF-06 \n \n1X500", "SCF-08 \n \n1X200",
						"140X140 \n55 \n250 \n1X300",
						"140X140 \n70 \n375 \n1X300",
						"140X140 \n85 \n500 \n1X300",
						"140X140 \n85 \n500 \n1X300",
						"140X110 \n40 \n250 \n1X400",
						"155X125 \n45 \n375 \n1X450",
						"165X135 \n50 \n500 \n1X300",
						"185X150 \n60 \n750 \n1X300",
						"195X165 \n65 \n1000 \n1X300",
						"245X195 \n65 \n1500 \n1X200",
						"W/SAUCE POT & FORK \n \n \n1X200" };

				String[] delicontainerimages = { "tplbk007", "tplbk008",
						"tplbk002", "tplbk009", "tplbk003", "tplmc069",
						"tplbk020", "tplbk068", "tplbk069", "tplbk070",
						"tplsh010", "tplsh011", "tplsh012", "tplsh013",
						"tplsh009", "tplsh014", "tplsh016", "tplsh017",
						"tplsh018", "tplsh019", "tplsh020", "tplch103",
						"tplch104", "tplch105", "tplch106", "tplch080",
						"tplch081", "tplch082", "tplch083", "tplch084",
						"tplch085", "tplch086", "tplch141" };

				// arrayListImages.add(delicontainerimages);
				// arrayListDesc.add(DeliContainer);

				arrayImages = delicontainerimages;
				arrayDescs = DeliContainer;

				delicontainerimages = null;
				DeliContainer = null;

			} else if (pos == 4) {

				String[] SaladContainer = { "175X115 \n40 \n250 \n1X400",
						"175X115 \n50 \n350 \n1X400",
						"190X140 \n55 \n500 \n1X300",
						"190X140 \n75 \n750 \n1X300",
						"190X140 \n70 \n1000 \n1X144",
						"120X120 \n55 \n180 \n1X450",
						"120X120 \n75 \n250 \n1X450",
						"130X130 \n85 \n375 \n1X450",
						"145X145 \n85 \n500 \n1X300",
						"160X160 \n95 \n750 \n1X300",
						"170X170 \n100 \n1000 \n1X300", "250[KRD] \n1X400",
						"400[KRD] \n1X400", "600[KRD] \n1X200",
						"800[KRD] \n1X200", "1000[KRD] \n1X200",
						"8[KRD] \n1X400", "12[KRD] \n1X400", "16[KRD] \n1X300",
						"24[KRD] \n1X200", "32[KRD] \n1X200", "2 SEC \n1X300",
						"3 SEC DIAGONAL \n 1X300", "3 SEC \n1X600",
						"W/ LID 390 CC \n190X140 \n45 \n390 \n1X400",
						"W/ LID 600 CC \n190X140 \n65 \n600 \n1X400",
						"CLEAR LID  \n190X140 \n25 \n- \n1X400",
						"CLEAR LID W/FORK  \n190X140 \n25 \n- \n1X400",
						"BLK BASE 600 CC  \n190X140 \n65 \n600 \n1X400",
						"Single V00492 \n168X54X85 \n1X980",
						"Double V00491 \n168X64X85 \n1X840",
						"STD. -[X10870] \n165X55 \n80 \n1X500",
						"[SL1610] \n165X55 \n80 \n1X600",
						"DEEP FILL -[X0288] \n165X55 \n80 \n1X600",
						" \n \n1X1000", "175X115X50 \n1X400",
						"7\" \n225X190.5X59.5 \n1X600",
						"9\" \n262X189.5X59.5 \n1X600",
						"SMALL-[X7518] \n180X75 \n75 \n1X480",
						"MEDIUM-[X7524] \n240X75 \n75 \n1X360",
						"LARGE-[X7530] \n300X75 \n75 \n1X320",
						"145X125 \n30 \n250 \n1X450",
						"145X125 \n- \n375 \n1X450",
						"145X125 \n55 \n500 \n1X600",
						"145X125 \n60 \n500 \n1X450",
						"185X145 \n45 \n600 \n1X300",
						"185X145 \n50 \n750 \n1X300",
						"185X145 \n65 \n1000 \n1X300",
						"245X195 \n50 \n1200 \n1X200",
						"245X195 \n60 \n1500 \n1X200", "8 \n1X240",
						"12 \n1X240", "16 \n1X240", "24 \n1X200", "28 \n1X200",
						"32 \n1X200", "[CB24] \n64X38.8X41.5 \n1X360",
						"[CB32] \n64X39.4X41.5 \n1X360",
						"[CB48] \n64X25.8X41.5 \n1X360", "" };
				String[] saladcontainerimages = { "tplch098", "tplch099",
						"tplch131", "tplch132", "tplch102", "tplch112",
						"tplch113", "tplch114", "tplch115", "tplch116",
						"tplch117", "tplch157", "tplch158", "tplch159",
						"tplch160", "tplch161", "tplch162", "tplch163",
						"tplch164", "tplch165", "tplch166", "tplch138",
						"tplch139", "tplcc019", "tplcc105", "tplcc106",
						"tplli159", "tplli160", "tplbk084", "tplbk049",
						"tplbk048", "tplbk087", "tplbk086", "tplbk085",
						"tplbk101", "tplbk050", "tplbk045", "tplbk046",
						"tplbk088", "tplbk089", "tplbk090", "tplch148",
						"tplch149", "tplch089", "tplch150", "tplch151",
						"tplch152", "tplch153", "tplch154", "tplch155",
						"tplch156", "tplch123", "tplch124", "tplch125",
						"tplch126", "tplch127", "tplch128", "tplbk081",
						"tplbk082", "tplbk083" };

				// arrayListImages.add(saladcontainerimages);
				// arrayListDesc.add(SaladContainer);

				arrayImages = saladcontainerimages;
				arrayDescs = SaladContainer;

				saladcontainerimages = null;
				SaladContainer = null;

			} else if (pos == 5) {

				String[] MicrowaveContainer = { "F10 \n225 \n120X50 \n1X250",
						"F16 \n450 \n120X60 \n1X250",
						"F20 \n525 \n120X80 \n1X250",
						"F10 \n625 \n120X95 \n1X250",
						"500  \n170X120X40 \n1X250",
						"650  \n170X120X50 \n1X250",
						"750  \n170X120X60 \n1X250",
						"1000  \n170X120X70 \n1X250",
						"1500  \n200X145X75 \n1X250",
						"Sq 4 Section  \n- \n1X150",
						"Sq 3 Section  \n- \n1X150",
						"Rect. 2 Section  \n- \n1X200",
						"Rect. 1623 \n750 \n1X200",
						"Rect. 1623 Section  \n1200 \n1X200",
						"1000 \n222X155X57 \n1X150",
						"1000 \n223X195X45 \n1X150",
						"900 \n220X140X48 \n1X150", "16 \n1X150", "22 \n1X150",
						"24 \n1X150", "32 \n1X150", "37 \n1X150", "42 \n1X150",
						"48 \n1X150", "48-3Sec \n1X150", "16 \n1X150",
						"22 \n1X150", "24 \n1X150", "32 \n1X150", "37 \n1X150",
						"42 \n1X150", "48 \n1X150", "48-3Sec \n1X150",
						"12 \n1X150", "16 \n1X150", "28 \n1X150", "32 \n1X150",
						"38 \n1X150", "89 \n1X150", "32oz/2SEC \n1X150",
						"53oz/3SEC \n1X150", "12 \n1X150", "12 \n1X300",
						"16 \n1X150", "28 \n1X150", "32 \n1X150", "38 \n1X150",
						"89 \n1X150", "32oz/2SEC \n1X150", "53oz/3SEC \n1X150",
						"8300 \n12 \n140X114X38 \n1X150",
						"8316 \n16 \n42.4X20.5X50.5 \n1X150",
						"8322 \n24 \n184X114X51 \n1X150",
						"8388 \n34 \n22.7X15.8X7.8 \n1X150",
						"2SEC(8228) \n- \n49.5X23.5X56.5 \n1X150",
						"868 \n24 \n230X158X52 \n1X200",
						"8366 \n28 \n225X156X35	 \n1X150",
						"888 \n34 \n230X158X70 \n1X200",
						"8388 \n38 \n225X156X40 \n1X150",
						"Plain \n- \n238X203X38 \n1X250",
						"2 Section \n- \n238X203X38 \n1X250",
						"3 Section \n- \n238X203X38 \n1X250",
						"4604 \n8 \n1X250", "4616 \n16 \n1X250",
						"4624 \n24 \n1X250", "4632 \n32 \n1X250",
						"8311 \n16 \n48.5X17.5X54.0 \n1X150",
						"8377 \n24 \n56.0X19.0X53.6 \n1X150",
						"8337 \n35 \n65.0X23.0X54.5 \n1X150",
						"9999 \n48 \n73.2X24.5X52.8 \n1X150",
						"9333 (3 SEC) \n16 \n \n1X150" };

				String[] microwavecontainerimages = { "tplmc025", "tplmc028",
						"tplmc031", "tplmc022", "tplmc014", "tplmc017",
						"tplmc020", "tplmc010", "tplmc012", "tplmc046",
						"tplmc047", "tplmc048", "tplmc045", "tplmc044",
						"tplmc094", "tplmc095", "tplmc096", "tplmc083",
						"tplmc137", "tplmc081", "tplmc079", "tplmc135",
						"tplmc132", "tplmc131", "tplmc129", "tplmc082",
						"tplmc136", "tplmc080", "tplmc078", "tplmc134",
						"tplmc132", "tplmc130", "tplmc128", "tplmc091",
						"tplmc127", "tplmc077", "tplmc075", "tplmc123",
						"tplmc121", "tplmc073", "tplmc071", "tplmc092",
						"tplmc112", "tplmc126", "tplmc076", "tplmc074",
						"tplmc122", "tplmc120", "tplmc072", "tplmc070",
						"tplmc050", "tplmc084", "tplmc051", "tplmc021",
						"tplmc085", "tplmc054", "tplmc052", "tplmc055",
						"tplmc053", "tplmc003", "tplmc008", "tplmc002",
						"tplmc108", "tplmc109", "tplmc110", "tplmc111",
						"tplmc086", "tplmc087", "tplmc088", "tplmc089",
						"tplmc090" };

				// arrayListImages.add(microwavecontainerimages);
				// arrayListDesc.add(MicrowaveContainer);

				arrayImages = microwavecontainerimages;
				arrayDescs = MicrowaveContainer;

				microwavecontainerimages = null;
				MicrowaveContainer = null;

			}

		} else if (catPos == 2) {

			if (pos == 0) {

				String[] Foamboximages = { "tstfx014", "tstfx015", "tstfx016",
						"tstfx009", "tstfx008", "tstfx010", "tstfx011",
						"tstfx006", "tstfx010", "tstfx036", "tstfx035",
						"tstfx007", "tplli104", "tplli103", "tplli085",
						"tstft001", "tstft026", "tstft050" };
				String[] FoamBox = { "LB1\n 185X140X70\n 1X250",
						"LB2\n 240X200X90\n 1X100", "LB3\n 240X200X90\n 1X100",
						"SMALL\n 127X127X68\n 5X100",
						"LARGE\n 152X140X76\n 1X125",
						"MEDIUM\n 220X92X65\n 1X500",
						"LARGE\n 295X89X72\n 1X500", "BIG\n 235X89X72\n 1X500",
						"KABBAB\n 1X100", "16\n 50X10", "8\n 50X20",
						"4\n 50X20", "16\n 1X500", "8\n 1X500", "4\n 1X1000",
						"PZT L\n 340X38\n 1X100", "PZT M\n 340X38\n 1X100",
						"6SEC\n 1X100" };

				// arrayListImages.add(Foamboximages);
				// arrayListDesc.add(FoamBox);

				arrayImages = Foamboximages;
				arrayDescs = FoamBox;

				Foamboximages = null;
				FoamBox = null;

			} else if (pos == 1) {

				String[] Paperboximages = { "tppcx112", "tppcx113", "tppcx114",
						"tppcx122", "tppcx027", "tppcx030", "tppcx029",
						"tpppb057", "tpppb059", "tpppb058", "tpppb013",
						"tpppb012", "tpppb011", "tpppb017", "tpppb064",
						"tpppb001", "tpppb069", "tpppb088", "mppcx002",
						"mppcx004", "mppcx005", "mppcx012", "mppcx007",
						"mppcx001", "mppcx003", "mppcx006", "mppcx011",
						"mppcx010", "tppcx080", "tppcx081", "tppcx082",
						"tpppb089", "tpppb090", "tpppb091" };
				String[] PaperBox = { "SMALL\n 22 \n 1X100",
						"MEDIUM \n 28\n 1X100", "BIG \n 33\n 1X100",
						"TINY \n 15\n 1X200", "SMALL \n 22\n 1X200",
						"MEDIUM \n 28\n 1X200", "BIG \n 33\n 1X100",
						"32 \n 1X500", "46 \n 1X500", "85 \n 1X150", "10X250",
						"1X1000", "1X500", "JUMBO\n 1X500", "85\n 1X150",
						"130\n 1X100", "170\n 1X150", "LID TPLL1234\n 1X90",
						"15X15X10\n 1X100", "20X20X10\n 1X100",
						"25X25X12\n 1X100", "30X30X10\n 1X100",
						"35X35X12\n 1X100", "15X15X10 \n 1X100",
						"20X20X10 \n 1X100", "25X25X12 \n 1X100",
						"30X30X10 \n 1X100", "35X35X12 \n 1X100",
						"4cups \n 1X125", "6cups \n 1X100", "12cups \n 1X90",
						"26 \n 1X400", "32 \n 1X300", "43 \n 1X200" };

				// arrayListImages.add(Paperboximages);
				// arrayListDesc.add(PaperBox);

				arrayImages = Paperboximages;
				arrayDescs = PaperBox;

				Paperboximages = null;
				PaperBox = null;

			}

		} else if (catPos == 3) {

			if (pos == 0) {
				String[] Cutlery = { "Tablespoon \n 150 \n 40X50",
						"Fork\n 150 \n 40X50", "Knife \n 150\n 40X50",
						"Tea Spoon \n 115\n 40X50", "Spork \n 135\n 1X1000",
						"Table Spoon \n 165\n 40X50", "Fork \n 165\n 40X50",
						"Knife \n 165\n 40X50", "Tea Spoon \n 125 \n 20X100",
						"Table Spoon \n 40X50", "Fork \n 40X50",
						"Knife \n 40X50", "Tea Spoon \n 20X100",
						"Table Spoon \n 25X40", "Fork \n 25X40",
						"Knife \n 25X40", "Table Spoon \n 25x40",
						"Fork \n 25X40", "Knife \n 25X40",
						"Table Spoon \n 150\n 40X50", "Fork \n 150\n 40X50",
						"Knife \n 150 \n 40X50", "Table Spoon \n 180 \n 40X50",
						"Fork \n 180 \n 1X1000", "Knife \n 180 \n 1X1000",
						"Table Spoon \n 180 \n 40X50", "Fork \n 180 \n 1X1000",
						"Knife \n 180 \n 1X1000", "Table Spoon \n 1X1000",
						"Fork \n 1X1000", "Knife \n 1X1000",
						"Table Spoon \n 180 \n 20x50", "Fork \n 180 \n 20x50",
						"Knife \n 180 \n 20X50", "Spoon(JX141) \n 50X40",
						"Fork(JX141) \n 50X40", "Knife(JX141) \n 50X40",
						"Table Spoon \n 177 \n 10X50", "Fork \n 177 \n 10X50",
						"Knife \n 177 \n 10X50",
						"Coffee Spoon \n 128\n 10X100", "95\n 1X10 KG",
						"CLEAR \n 20X50", "BLACK \n 20X50", "GREEN \n 20X50",
						"White \n 95 \n 10X200",
						"Ind.White/Spoon/fork/Knife/napkin \n 1X500",
						"Ind.Black/Spoon/fork/knife/napkin \n 1X500" };
				String[] Cutleryimages = { "tplct013", "tplct007", "tplct009",
						"tplct015", "tplct011", "tplct027", "tplct025",
						"tplct026", "tplct028", "tplct092", "tplct093",
						"tplct094", "tplct095", "tplct077", "tplct078",
						"tplct079", "tplct080", "tplct081", "tplct082",
						"tplct014", "tplct008", "tplct010", "tplct084",
						"tplct086", "tplct085", "tplct003", "tplct001",
						"tplct002", "tplct101", "tplct102", "tplct103",
						"tplct104", "tplct105", "tplct106", "tplct109",
						"tplct110", "tplct111", "tplct030", "tplct032",
						"tplct033", "tplct031", "tplic047", "tplct096",
						"tplct097", "tplct098", "tplct006", "minpr033",
						"minpr005" };

				// arrayListImages.add(Cutleryimages);
				// arrayListDesc.add(Cutlery);

				arrayImages = Cutleryimages;
				arrayDescs = Cutlery;

				Cutleryimages = null;
				Cutlery = null;

			} else if (pos == 1) {

				String[] plates = { "M9 \n 18 \n 50X10", "M8 \n 22 \n 50X10",
						"M7 \n 26 \n 10X50", "M6 \n 26 \n 1X500",
						"22 \n 1X500", "26 \n 1X500", "19 \n 1X216",
						"26 \n 1X216", "19 \n 1X216", "26 \n 1X216",
						"13 \n 1X144", "20.5 \n 1X144", "27 \n 1X144",
						"13 \n 1X144", " 20.5 \n 1X144", "27 \n 1X44",
						"M15 \n 1X1000", "M13 \n 1X500", "300 \n 1X1000",
						"500 \n 1X500", "700 \n 1X600", "1100 \n 1X300",
						"6\" \n 1X1000", "7\"8\" \n 40X25", "9\" \n 20X25",
						"10\" \n 1X500", "7\" \n 1X500", "9\" \n 1X500",
						"10\" \n 1X500", "9\" \n 5X100", "10\" 1X500",
						"8\" \n 1X500", "12\" 1X500", "7\" \n 12X100",
						"9\" \n 12X100", "7\" \n 10X100", "9\" \n 10X100",
						"6\" \n 125X8", "7\" \n 125X8", "9\" \n 125X4",
						"10\" 125X4", "10\"3 Sec \n 120X4",
						"Hamburger Box \n 50X10", "Lunch Box Small \n 50X12",
						"Lunch Box Medium \n 50X4", "Lunch Box Large \n 50X4",
						"Lunch Box 3 Sec \n 50X4", "Lunch Box 2 Sec \n 50X5" };
				String[] platesimages = { "tplpl061", "tplpl062", "tplpl032",
						"tplli010", "tplpl029", "tplpl031", "tplpl072",
						"tplpl070", "tplpl071", "tplpl069", "tplpl067",
						"tplpl065", "tplpl063", "tplpl068", "tplpl066",
						"tplpl064", "tplbw005", "tplbw002", "tplbw014",
						"tplbw015", "tplbw018", "tplbw019", "tstfp003",
						"tstfp004", "tstfp013", "tstfp007", "tstfp018",
						"tstfp019", "tstfp020", "tstfp008", "tstfp012",
						"tstfb005", "tstfb002", "mppfp003", "mppfp001",
						"tpppp002", "tpppp004", "tbdpp001", "tbdpp002",
						"tbdpp003", "tbdpp004", "tbdpp005", "tbdpp006",
						"tbdpp007", "tbdpp008", "tbdpp009", "tbdpp010",
						"tbdpp011" };

				// arrayListImages.add(platesimages);
				// arrayListDesc.add(plates);

				arrayImages = platesimages;
				arrayDescs = plates;

				platesimages = null;
				plates = null;

			} else if (pos == 2) {

				String[] Tray = { "18 \n 1X8", "20 \n 1X8", "24 \n 1X8",
						"27 \n 1X8", "30 \n 1X8", "33 \n 1X8", "36 \n 1X8",
						"40 \n 1x8", "NO.1 \n 230 \n 162X120X23 \n 10",
						"NO.2 \n 320 \n 193X135X23 \n 10",
						"NO.3 \n 500 \n 225X162X24 \n 10",
						"NO.4 \n 900\n 268X187X30 \n 10",
						"NO.5 \n 1500 \n 305X215X34 \n 10", "250 \n 20X50",
						"400 \n 20X50", "500 \n 20X50", "250/400/500 \n 20X50",
						"No.7 \n \n 10", "Small \n 165X147X25 \n 1X500",
						"Medium \n 195X145X25 \n 4X25",
						"Large \n 230X160X25 \n 4X125",
						"Extra Large \n 265X235X40 \n 1X250",
						"M2 \n 178X133X25 \n 1X500",
						"M3 \n 220X132X17 \n 1X500",
						"M13 \n 216X152X20 \n 1X500",
						"M14 \n 215X178X20 \n 1X250",
						"D18 \n 265X189X20 \n 1X250",
						"Pt 1 \n 167X129X25 \n 1X500",
						"M-T \n 131X96X20 \n 1X500",
						"Jumbo(T-J) \n 320X235X40 \n 1X200",
						"Yellow(M13) \n 216X152X20 \n 1X500",
						"Black(D18) \n 265X189X20 \n 1X250",
						"Green(D18) \n 265X189X20 \n 1X250",
						"Blue(D18) \n 265X189X20 \n 1X250",
						"Green(M13) \n 216X152X20 \n 1X500",
						"Blue(M13) \n 216X152X20 \n 1X500",
						"Yellow (M14) \n 215X178X20 \n 1X250",
						"PT 1 \n 167X129X25 \n 1X500",
						"14 M \n 215X178X20 \n 1X250",
						"13 m \n 216X152X20 \n 1X500",
						"3 M \n 220X132X17 \n 1X500",
						"18 D \n 265X189X20 \n 1X250",
						"14 M \n 215X178X20 \n 1X250",
						"13 M \n 216X152X20 \n 1X250",
						"3 M \n 220X132X17 \n 1X500",
						"13 M \n 216x152X20 \n 1X500",
						"18 DV 	\n266X191X23 \n 1X250",
						"JUMBO \n 318X230X38 \n 1X100",
						"BLUE M 14 \n 215X178X20 \n 1X250",
						"18 DV \n 266X191X23 \n 1X250",
						"JUMBO \n 313X232X40 \n 1X100",
						"M 3 \n 220X132X17 \n 1X500",
						"M 14 \n 215X178X20 \n 1X250", "150X80 \n 1X3000",
						"100X127 \n 1X3000", "100X165 \n 1X3000",
						"#1 \n 17.5X12.05 \n 1X10", "#3A \n 18.5X27 \n 1X10",
						"#2 \n 20X14 \n 1X10", "#3 \n 23X16 \n 1X10",
						"#4 \n 29X20 \n 1X10", "#5 \n 31X22 \n 1X10",
						"#6 \n 35X24 \n 1X10", "#7 \n 38X26 \n 1X10",
						"#8 \n 42X31 \n 1X10", "#9 \n 47X34 \n 1X10",
						"#10 \n 51X37 \n 1X10", "#3 \n 23X16 \n 1X10",
						"#4 \n 29X20 \n 1X10", "#5 \n 31X22 \n 1X10",
						"#6 \n 35X24 \n 1X10", "#7 \n 38X26 \n 1X10",
						"#8 \n 40.5X30 \n 1X10", "26 \n 1X10", "30 \n 1X10" };
				String[] Trayimages = { "tplcr091", "tplcr092", "tplcr093",
						"tplcr094", "tplcr095", "tplcr096", "tplcr097",
						"tplcr098", "mplpr001", "mplpr003",
						"mplpr004", "mplpr005", "mplpr007", "mplpr008",
						"mplpr009", "mplpr010", "tplpc016", "tstft022",
						"tstft019", "tstft011", "tstft023", "tstft016",
						"tstft017", "tstft012", "tstft014", "tstft007",
						"tstft020", "tstft005", "tstft009", "tstft051",
						"tstft036", "tstft052", "tstft053", "tstft054",
						"tstft055", "tstft058", "tstft059", "tstft043",
						"tstft042", "tstft035", "tstft018", "tstft039",
						"tstft041", "tstft038", "tstft037", "tstft045",
						"tstft047", "tstft049", "tstft060", "tstft046",
						"tstft048", "tstft030", "tstft031", "thppw006",
						"thppw049", "thppw050", "tppct001", "tppct002",
						"tppct003", "tppct004", "tppct005", "tppct006",
						"tppct007", "tppct008", "tppct009", "tppct010",
						"tppct011", "tppct012", "tppct013", "tppct014",
						"tppct015", "tppct016", "tppct017", "tppct020",
						"tppct021" };

				// arrayListImages.add(Trayimages);
				// arrayListDesc.add(Tray);

				arrayImages = Trayimages;
				arrayDescs = Tray;

				Trayimages = null;
				Tray = null;

			} else if (pos == 3) {

				String[] cups = { "100 \n 20X50", "120 \n 20X50",
						"160 \n 20X50", "205 \n 20X50", "250 \n 20X50",
						"300 \n 20X50", "390 \n 20X50", "575 \n 16X40",
						"350 \n 20X50", "400 \n 20X50", "500 \n 20X40",
						"250 \n 20X50", "330 \n 25X50", "400 \n 20X50",
						"160/205/250/300cc \n 10X100",
						"350/390/400/500cc \n 10X100", "500cc \n 20X40",
						"575cc \n 16X40", "350/390/400/500cc \n 10X100",
						"3 \n 1X2500", "8 \n 40X50", "10 \n 40X50",
						"12 \n 1X1000", "14 \n 20X50", "16 \n 1X1000",
						"16 \n 1X1000", "20 \n 20X50", "10 \n 1X600",
						"12 \n 1X600", "14 \n 1X600", "16 \n 1X600",
						"8/10 \n 40x50", "12 \n 1X1000", "14/16/20 \n 1X1000",
						"16 \n 1X1000", "Flat \n 1X1000", "8/10 \n 40X50",
						"12 \n 1X1000", "14/16/20 \n 1X1000", "16 \n 1X1000",
						"Dome \n 1X1000", "10 (300cc) \n 1X1000",
						"12 (360cc) \n 1X1000", "14 (400cc) \n 1X1000",
						"16 (500cc) \n 1X1000", "20 (600cc) \n 1X1000)",
						"10 (300cc) \n 1X1000", "12 (360cc) \n 1X1000",
						"14 (400cc) \n 1X1000", "16 (500cc) \n 1X1000",
						"20 (600cc) \n 1X1000", "12 (360cc) \n 20X50",
						"16 (500) \n 20X50", "Dome \n 10X100",
						"Flat \n 10X100", "6 \n 20X50", "7 \n 1X1000",
						"4 \n 1X2000", "RED \n 230 \n 1X500",
						"ORANGE \n 230 \n 1X500", "BLUE \n 230 \n 1X500",
						"GREEN \n 230 \n 1X500", "VIOLET \n 230 \n 1X500",
						"20X25", "1X500", "1X500", "200 \n 1X500",
						"ART421 Round \n 58 \n 1X300",
						"ART420 w/ Lid \n 58 \n 1X650",
						"ART426 w/Lid \n 120 \n 1X500",
						"ART425 w/Lid \n 200\n 1X400", "80 \n 8X50",
						"100 \n 8X50", "150 \n 8X50",
						"Lid-TPLLI222 \n 90 \n 25X10",
						"Lid-TPLLI224 \n 130 \n 25X10",
						"Lid-TPPLI236 \n 185 \n 20X50", "DOM LIDS \n 20X50",
						"Lid-TPLLI237 \n 230 \n 20X50", "FLAT LIDS \n 20X50",
						"2X100", "5X50", "NO.1 \n 130 \n 1X600",
						"NO.2 \n 170 \n 1X600", "NO.3 \n 210 \n 1X400",
						"NO.1 \n 130 \n 1X600", "NO.2 \n 170 \n 1X600",
						"NO.3 \n 210 \n 1X400", "16 \n 20X50", "12 \n 20X50",
						"8 \n 20X50", "6 \n 20X50", "4 \n 20X50",
						"16 \n 20X50", "12 \n 20X50", "8 \n 20X50",
						"6 \n 20X50", "4 \n 20X50", "16 \n 20X50",
						"12 \n 20X50", "8 \n 20X50", "6 \n 20X50",
						"4 \n 20X50", "270 \n 1X900", "750 \n 1X900",
						"200 \n 1X1200", "300 \n 1X800", "150 \n 1X1200",
						"250 \n 1X800", "350 \n 1X600",
						"Lid-TPLLI223 \n 90 \n 32X50",
						"Lid-TPLLI223 \n 130 \n 32X50", "4 \n 25X40",
						"6 \n 25X40", "8 \n 25X40", "10 \n 1X1000",
						"12 \n 1X1000", "14 \n 25X40", "16 \n 1X1000",
						"6 \n 1X1000", "8 \n 20X50", "10 \n 1X1000",
						"12 \n 1X1000", "14 \n 1X1000", "16 \n 1X1000", "",
						"8 \n 20X50", "12 \n 20X50", "16 \n 20X50",
						"8 \n 20X50", "12 \n 20X50", "16 \n 20X50",
						"4 \n 20X50", "8 \n 20X50", "12 \n 20X50",
						"16 \n 20X50", "4 \n 20X50", "8 \n 20X50",
						"12 \n 20X50", "16 \n 20X50", "4 \n 20X50",
						"8 \n 20X50", "12 \n 20X50", "16 \n 20X50",
						"4 \n 20X50", "8 \n 20X50", "12 \n 20X50",
						"16 \n 20X50", "8 \n 1X1000", "12 \n 1X1000",
						"16 \n 1X600", "8 \n 1X1000", "12 \n 1X1000",
						"16 \n 1X600", "8 \n 1X1000", "12 \n 1X1000",
						"16 \n 1X1000", "8 \n 1X1000", "12 \n 1X1000",
						"16 \n 1X1000", "8 \n 50X20", "8 \n 1X1000",
						"12 \n 1X1000", "16 \n 1X1000", "8 \n 20X50",
						"8 \n 20X50", "12 \n 20X50", "16 \n 1X1000",
						"4 \n 1X1000", "8 \n 1X1000", "12 \n 1X1000",
						"16 \n 1X600", "4 \n 1X1000", "8 \n 1X1000",
						"12 \n 1X1000", "16 \n 1X1000",
						"(PLAIN)8 OZ \n 1X1000", "(PLAIN)12 OZ \n 1X1000",
						"(PLAIN)16 OZ \n 1X600", "(BROWN)4 OZ \n 1X1000",
						"(WHITE)4 OZ 1X1000", "4.5 \n 20X250", "8 OZ \n 20X50",
						"12 OZ \n 20X50", "16 OZ \n 12X50", "8 OZ \n 1X1000",
						"12 OZ \n 1X1000", "16 OZ \n 1X600", "4 OZ \n 1X1000",
						"8 OZ \n 1X1000", "12 OZ \n 1X1000", "16 OZ \n 1X600",
						"12 OZ 1X500", "16 OZ \n 1X500",
						"4 OZ[BLACK] \n1X1000 ", "8 OZ[BLACK] \n1X1000 ",
						"12/16 OZ[BLACK] \n1X1000 ", "16 OZ[BLACK] \n1X600 ",
						"4 OZ[BLACK] \n1X1000 ", "8 OZ[BLACK] \n1X1000 ",
						"12/16 OZ[BLACK] \n1X1000 ", "16 OZ[BLACK] \n1X600 ",
						"12 \n 20X50", "16 \n 20X50", "12 \n 20X50",
						"16 \n 1X1000", "(BROWN) \n 2.5 OZ \n 40X50",
						"(WHITE) \n 2.5 OZ \n 40X50", "4 \n 1X1000",
						"6 \n 20X50", "7 \n 20X50", "7 \n 20X50", "9 \n 20X50",
						"8 OZ CUP \n 1X2000", "12/16 OZ CUP \n 1X2000",
						"16(S) \n 1X500", "26(M) \n 1X500", "32(L) \n 1X500",
						"16(S) \n 1X500", "26(M) \n 1X500", "32(L) \n 1X500" };
				String[] cupsimages = { "tplcu044", "tplcu045", "tplcu046",
						"tplcu047", "tplcu049", "tplcu050", "tplcu052",
						"tplcu056", "tplcu051", "tplcu053", "tplcu054",
						"tplcu145", "tplcu146", "tplcu147", "tplli054",
						"tplli056", "tplli057", "tplli058", "tplli016",
						"tplcu138", "tplcu066", "tplcu060", "tplcu061",
						"tplcu062", "tplcu063", "tplcu064", "tplcu065",
						"tplcu123", "tplcu124", "tplcu124", "tplcu126",
						"tplli024", "tplli021", "tplli022", "tplli023",
						"tplli031", "tplli018", "tplli105", "tplli017",
						"tplli106", "tplli030", "tplcu109", "tplcu110",
						"tplcu112", "tplcu113", "tplcu027", "tplcu028",
						"tplcu029", "tplcu030", "tplcu031", "tplcu033",
						"tplcu034", "tplli001", "tplli002", "mplpr006",
						"tplcu073", "tplcu070", "tplcu132", "tplcu133",
						"tplcu134", "tplcu135", "tplcu136", "mplrt001",
						"tplif008", "tplif016", "tplic005", "tplcr076",
						"tplcr074", "tplcr080", "tplcr078", "tplcr087",
						"tplcr088", "tplcr089", "tplcr084", "tplcr090",
						"tplcu143", "tplli235", "tplcu144", "tplli238",
						"tplcr082", "tplcr083", "tplic018", "tplic019",
						"tplic021", "tplli060", "tplli062", "tplli064",
						"tppcu068", "tppcu067", "tppcu066", "tppcu065",
						"tppcu091", "tplli157", "tplli155", "tplli153",
						"tplli151", "tplli201", "tplli158", "tplli156",
						"tplli154", "tplli152", "tplli200", "tplic056",
						"tplli216", "tplic041", "tplic042", "tplic030",
						"tplic031", "tplic032", "tplcr085", "tplcr086",
						"tstfc006", "tstfc007", "tstfc008", "tstfc002",
						"tstfc003", "tstfc001", "tstfc005", "tplli047",
						"tplli048", "tplli041", "tplli043", "tplli044",
						"tplli045", "tppcu062", "tppcu063", "tppcu064",
						"tplli148", "tplli149", "tplli150", "tppcu088",
						"tppcu075", "tppcu076", "tppcu077", "tplli128",
						"tplli075", "tplli073", "tplli073", "tppcu089",
						"tppcu023", "tppcu022", "tppcu021", "tplli128",
						"tplli075", "tplli073", "tplli073", "tppcu098",
						"tppcu099", "tppcu100", "tplli128", "tplli075",
						"tplli074", "tppcu048", "tppcu049", "tppcu050",
						"tplli123", "tplli124", "tplli125", "tppcu004",
						"tppcu058", "tppcu059", "tppcu060", "tplli029",
						"tplli141", "tplli141", "tplli142", "tplli143",
						"tppcu052", "tppcu028", "tppcu026", "tppcu027",
						"tplli128", "tplli075", "tplli073", "tplli074",
						"tppcu053", "tppcu054", "tppcu055", "tppcu052",
						"tppcu052", "tppcu007", "tppcu082", "tppcu083",
						"tppcu084", "tppcu079", "tppcu080", "tppcu081",
						"tppcu111", "tppcu112", "tppcu113", "tppcu114",
						"tppcu115", "tppcu116", "tplli228", "tplli229",
						"tplli230", "tplli231", "tplli239", "tplli240",
						"tplli241", "tplli242", "tppcu009", "tppcu010",
						"tplli069", "tplli070", "tppcu090", "tppcu092",
						"tppcu012", "tppcu057", "tppcu061", "tppcu025",
						"tppcu019", "tpppb065", "tpppb066", "tpppb082",
						"tpppb083", "tpppb084", "tpppb085", "tpppb086",
						"tpppb087" };

				// arrayListImages.add(cupsimages);
				// arrayListDesc.add(cups);

				arrayImages = cupsimages;
				arrayDescs = cups;

				cupsimages = null;
				cups = null;

			} else if (pos == 4) {

				String[] disposable = {
						"5X243 \n 40X225",
						"6X227 \n 20X500",
						"6X230 \n 20X500",
						"6X230 \n 20X500",
						"6X233 \n 20X500",
						"6X228 \n 20X500",
						"7X230 \n 20X500",
						"8X206 \n 1X10000",
						"Straight/Transparent \n 8X230 \n 20X500",
						"Straight/White with Colour Stripe \n 8X230",
						"20X500",
						"Straight/Black \n 8X230",
						"20X500",
						"Spoon Straw Straight/White with Colour Stripe \n 8X233 \n 20X500",
						"Artistic Mixed Colour \n 6X260 \n 40X100",
						"Clear \n 110 \n 1X5000", "White \n 110 \n 1X5000",
						"Clear 6\" \n 152 \n 10X100",
						"Clear 7\" \n 175 \n 1X1000", "220 \n 174 \n 1X1000",
						"140 \n 6X2000", "For 2 Cups \n 1X400",
						"For 4 Cups \n 1X300", "Clear \n 1X500",
						"Black \n 1X500", "9.5(65 mm) \n 16X200",
						"10.5(70mm) \n 16X200", "48X25", "80X7 Py \n 10X500",
						"90X7 Ply \n 10X500" };
				String[] disposableimages = { "tplsw003", "tplsw008",
						"tplsw009", "tplsw010", "tplsw007", "tplsw011",
						"tplsw013", "tplsw015", "tplsw023", "tplsw024",
						"tplsw025", "tplsw026", "tplsw021", "tplst011",
						"tplst012", "tplst001", "tplst002", "tplst016",
						"twpst033", "tpppb067", "tpppb068", "tplpb029",
						"tplpb030", "tpppb021", "tpppb020", "mpprt008",
						"tppnt004", "tppnt005" };

				// arrayListImages.add(disposableimages);
				// arrayListDesc.add(disposable);

				arrayImages = disposableimages;
				arrayDescs = disposable;

				disposableimages = null;
				disposable = null;

			}

		} else if (catPos == 4) {

			if (pos == 0) {

				String[] Toothpick = { "Double Ended \n 144X500",
						"S/P Paper(16A) \n 12X1000",
						"S/P Cellophane(16B) \n 12X1000", "Minted \n 144X400",
						"S/P Minted \n 12X1000", "2.5\" \n 10x1000",
						"4\" \n 10X1000", "10 \n 1x144", "10 \n 1X144",
						"10 \n 1X144", "15 \n 1X100", "15 \n 1X100" };
				String[] Toothpickimgs = { "TWPFT001", "TWPFT005", "TWPFT003",
						"TWPFT002", "TWPFT004", "TWPST031", "TWPST030",
						"TWPST048", "TWPST049", "TWPST016", "TWPST013",
						"TWPST008" };

				// arrayListImages.add(Toothpickimgs);
				// arrayListDesc.add(Toothpick);

				arrayImages = Toothpickimgs;
				arrayDescs = Toothpick;

				Toothpickimgs = null;
				Toothpick = null;

			} else if (pos == 1) {
				String[] Skewer = { "6\" \n 200X100", "8\" 200X100",
						"10\" \n 200X100", "12\" \n 100X100", "8\" \n 100X100",
						"10\" \n 100X100", "12\" \n 100X100", "10X100",
						"Single Wrap \n 1X2000", "12X1000", "20X100", "20X100" };
				String[] Skewerimages = { "twpst005", "twpst006", "twpst003",
						"twpst004", "twpst055", "twpst056", "twpst039",
						"twpst022", "twpst036", "twpst047", "twpst054",
						"twpst057" };

				// arrayListImages.add(Skewerimages);
				// arrayListDesc.add(Skewer);

				arrayImages = Skewerimages;
				arrayDescs = Skewer;

				Skewerimages = null;
				Skewer = null;

			} else if (pos == 2) {
				String[] BambooPicks = { "9 CM \n 100X10", "15 Cm \n 100X10",
						"12 CM \n 100X10", "18 CM \n 100X10" };
				String[] Bamboopicksimags = { "twpst050", "twpst051",
						"twpst052", "twpst053" };

				// arrayListImages.add(Bamboopicksimags);
				// arrayListDesc.add(BambooPicks);

				arrayImages = Bamboopicksimags;
				arrayDescs = BambooPicks;

				Bamboopicksimags = null;
				BambooPicks = null;

			} else if (pos == 3) {

				String[] Doyley = { "6.5X9\" \n 8X250", "7X10\" \n 8X250",
						"7.5\"X10\" \n 8X250", "8\"X12\" \n 8X250",
						"8.5\"X12.5\" \n 8X250", "10.5\"X14\" \n 8X250",
						"7.5\"X9\" \n 8X250", "5.5\"X9.5\" \n 8X250",
						"7\"X12\" \n 8X250", "8\"X12\" \n 8X250",
						"10\"X14.5\" \n 8X250", "12\"X16\" \n 8X250",
						"14\"X18\" \n 4X250", "12.5\"X18.5\" \n 4X250",
						"3.5\" \n 20X250", "4\" \n 8X250", "4.5\" \n 8X250",
						"5\" 8X250", "5.5\" \n 8X250", "6\" \n 8X250",
						"6.5\" \n8X250 ", "7\" \n 8X250", "7.5\" \n 8X250",
						"8\" \n 8x250", "8.5\" \n 8X250", "9\" \n 8X250",
						"9.5\" \n 8X250", "10.5\" \n 8X250", "11.5\" \n 8X250",
						"12\" \n 8X250", "12.5\" \n 8X250", "13.5\" \n 8X250",
						"14.5\" \n 8X250", "15.5 \" \n 8X250",
						"16.5\" \n 4X250", "5\" \n 10X100", "6\" \n 10X100",
						"7\" \n 10X100", "10\" \n 10X100", "12 \" \n 10X100",
						"12.5\" \n 10X100", "3.5\" \n 2X500", "5\" \n 10X100",
						"5.5\" \n 8X250", "6\" \n 10X100", "7\" \n 10X100",
						"7.5\" \n 8X250", "8.5\" \n 10X100", "9.5\" \n 8X250",
						"10\" \n10X100", "10.5\" \n 8X250", "12.5\" \n 10X100" };
				String[] Doyleyimags = { "tppdy002", "tppdy003", "tppdy004",
						"tppdy005", "tppdy006", "tppdy007", "tppdy009",
						"tppdy010", "tppdy011", "tppdy012", "tppdy013",
						"tppdy014", "tppdy015", "tppdy016", "tppdy035",
						"tppdy039", "tppdy040", "tppdy041", "tppdy042",
						"tppdy044", "tppdy045", "tppdy046", "tppdy047",
						"tppdy048", "tppdy049", "tppdy052", "tppdy053",
						"tppdy017", "tppdy018", "tppdy021", "tppdy022",
						"tppdy024", "tppdy026", "tppdy029", "tppdy030",
						"tppdy020", "tppdy028", "tppdy031", "tppdy034",
						"tppdy036", "tppdy038", "tppdy050", "tppdy019",
						"tppdy055", "tppdy027", "tppdy082", "tppdy056",
						"tppdy032", "tppdy057", "tppdy033", "tppdy054",
						"tppdy037" };

				// arrayListImages.add(Doyleyimags);
				// arrayListDesc.add(Doyley);

				arrayImages = Doyleyimags;
				arrayDescs = Doyley;

				Doyleyimags = null;
				Doyley = null;

			} else if (pos == 4) {
				String[] PlaceMat = { "9.5X14 \n 4X250",
						"9.5X14 Blue/Blue Check \n 4X250",
						"9.5X14 Red \n 4X250", "20X14 Red 4X250",
						"20X14 Blue/ Blue Check \n 4X250",
						"20X14 White \n 4X250", "14\"X9.5\" \n 1X1000" };
				String[] PlaceMatimgs = { "tppdy075", "tppdy076", "tppdy077",
						"tppdy078", "tppdy079_Tray_Mat", "tppdy080_tray_mat",
						"tppdy063_tray_mat" };

				// arrayListImages.add(PlaceMatimgs);
				// arrayListDesc.add(PlaceMat);

				arrayImages = PlaceMatimgs;
				arrayDescs = PlaceMat;

				PlaceMatimgs = null;
				PlaceMat = null;

			} else if (pos == 5) {

				String[] Napkin = { "30X30 \n 40X100", "33X33(2ply) \n 40X50",
						"40X40(2ply) \n 40X50", "24X24(2ply) \n 20X250",
						"Red \n 40X40 \n 20X50", "Yellow \n 40X40 \n 20X50",
						"Green \n 40X40 \n 20X50", "Blue \n 40X40 \n 20X50",
						"Black \n 40X40 \n 20X50", "Red \n 33X33 \n 24X50",
						"Yellow \n 33X33 \n 24X50", "Green \n 33X33 \n 24X50",
						"Blue \n 33X33 \n 24X50", "BLack \n 33X33 \n 24X50",
						"Red \n 25X25 \n 38X100", "Yellow \n 25X25 \n 34X100",
						"Green \n 25X25 \n 38X100", "Blue \n 25X25 \n 38X100",
						"Black \n 25X25 \n 38X100", "1X1000" };
				String[] Napkinimgs = { "tppnt054", "tppnt048", "tppnt049",
						"tppnt050", "tppnt080", "tppnt081", "tppnt082",
						"tppnt083", "tppnt151", "tppnt095", "tppnt096",
						"tppnt097", "tppnt098", "tppnt150", "tppnt153",
						"tppnt155", "tppnt154", "tppnt156", "tppnt152",
						"tppnt103" };

				// arrayListImages.add(Napkinimgs);
				// arrayListDesc.add(Napkin);

				arrayImages = Napkinimgs;
				arrayDescs = Napkin;

				Napkinimgs = null;
				Napkin = null;
			}

		} else if (catPos == 5) {

			if (pos == 0) {

				String[] Lunchboxsimages = { "tplif018", "tplif002",
						"tplif001_003", "tplif006", "tplif004", "tplif011",
						"tplif010", "tplif009", "talac068" };
				String[] LunchBox = { "1X400", "1X50", "10X10/1X300", "10X10",
						"1X120", "LidcodeTPLLI005 \n 90X75 \n 20X100/1X100",
						"LidcodeTPLLI006 \n 90X90 \n 1X1000",
						"LidcodeTPLLI004 \n 75X75 \n 1X1000",
						"5332/1028 \n1X1000" };

				// arrayListImages.add(Lunchboxsimages);
				// arrayListDesc.add(LunchBox);

				arrayImages = Lunchboxsimages;
				arrayDescs = LunchBox;

				Lunchboxsimages = null;
				LunchBox = null;

			} else if (pos == 1) {

				String[] Plasticcoffecupimages = { "tplif007" };
				String[] PlasticCoffeCup = { "150 \n 1X500" };

				// arrayListImages.add(Plasticcoffecupimages);
				// arrayListDesc.add(PlasticCoffeCup);

				arrayImages = Plasticcoffecupimages;
				arrayDescs = PlasticCoffeCup;

				Plasticcoffecupimages = null;
				PlasticCoffeCup = null;

			}

		} else if (catPos == 6) {

			if (pos == 0) {

				String[] Bakingmouldimages = { "tppbm002", "tppbm001",
						"tppbm024", "tppbm025", "tppbm006", "tppbm018",
						"tppbm020", "tppbm003", "tppbm028", "tppbm015",
						"tppbm016", "tppbm017", "tppbm004", "tppbm014",
						"tppbm011", "tppbm012", "tppbm013", "tppbm009",
						"tppbm008" };
				String[] BakingMould = { "225X185 \n1X720",
						"Art 302 \n190X95 \n1X750",
						"PM200 \n200X65X45 \n1X1000",
						"PM227 \n225X70X65 \n1X480",
						"PM150 \n150X65X505 \n1X480", "135X35 \n1X300",
						"AB1 \n330X225X60 \n1X320", "PB 225 \n225X70 \n1X540",
						"M200/60 \n220X60 \n1X540", "P 70/50  \n1X2000",
						"P 73/50  \n1X2000", "P 73/60  \n1X2000",
						"155X45  \n1X600", "MB.185/35 HW \n \n1X600",
						"MB.120/35 HW  \n1X300", "MB.155/35 HW  \n1X600",
						"MB.170/35 HW  \n1X600", "MB.200/35 HW \n1X600",
						"200X65 \n1X480" };

				// arrayListImages.add(Bakingmouldimages);
				// arrayListDesc.add(BakingMould);

				arrayImages = Bakingmouldimages;
				arrayDescs = BakingMould;

				Bakingmouldimages = null;
				BakingMould = null;

			} else if (pos == 1) {

				String[] Cakeboardimage = { "cake_board" };
				String[] Cakeboard = { "" };

				// arrayListImages.add(Cakeboardimage);
				// arrayListDesc.add(Cakeboard);

				arrayImages = Cakeboardimage;
				arrayDescs = Cakeboard;

				Cakeboardimage = null;
				Cakeboard = null;

			} else if (pos == 2) {
				String[] Muffintraysimages = { "tppbm023", "tppbm021",
						"tppbm022", "tppbm029", "tppbm030", "tppbm031",
						"tppbm032", "tppbm033", "tppbm034", "tppbm035",
						"tppbm036", "tppbm037", "tppbm038", "tppbm039",
						"tppbm040" };
				String[] MuffinTray = { "WHITE \nSMALL NTS 1 \n6X8 \n48X100",
						"WHITE \nMEDIUM NTS 2 \n6X4 \n24X125",
						"WHITE \nSMALL NTS 3 \n6X4 \n24X125",
						"WHITE \nSMALL[35 CUPS] \n35X90",
						"WHITE \nMEDIUM[24 CUPS] \n24X125",
						"BROWN \nSMALL[35 CUPS] \n35X90",
						"BROWN \nMEDIUM[24 CUPS] \n24X125",
						"DESIGNED \nSMALL[35 CUPS]BLUE \n35X90",
						"DESIGNED \nSMALL[35 CUPS]RED \n35X90",
						"DESIGNED BROWN \nSMALL[35 CUPS]BLUE \n35X90",
						"DESIGNED ORANGE\nSMALL[35 CUPS]BLUE \n35X90",
						"DESIGNED BLUE \nMEDIUM[24 CUPS] \n24X125",
						"DESIGNED RED \nMEDIUM[24 CUPS] \n24X125",
						"DESIGNED BROWN \nMEDIUM[24 CUPS] \n24X125",
						"DESIGNED ORANGE \nMEDIUM[24 CUPS] \n24X125" };

				// arrayListImages.add(Muffintraysimages);
				// arrayListDesc.add(MuffinTray);

				arrayImages = Muffintraysimages;
				arrayDescs = MuffinTray;

				Muffintraysimages = null;
				MuffinTray = null;

			} else if (pos == 3) {

				String[] Cakecupsimages = { "tppsc067", "tppsc068", "tppsc069",
						"tppsc070", "tppsc071", "tppsc072", "tppcc113",
						"tppcc114", "tppcc115_orange", "tppcc116",
						"tppcc114_brown", "tppcc115_white", "tppcc105",
						"tppcc107", "tppcc108", "tppcc109", "tppcc110",
						"tppcc111", "tppcc112", "tppcc083", "tppcc084",
						"tppcc086", "tppcc088", "tppcc077", "tppcc079",
						"tppcc081", "tppcc082", "tppcc012", "tppcc013",
						"tppcc014", "tppcc010", "tppcc011", "talcc011",
						"talcc011", "talcc012", "talcc013", "talcc014",
						"talcc015", "talcc007", "talcc008", "talcc009",
						"talcc010", "tppcc027", "tppcc029", "tppcc039",
						"tppcc015", "tppcc018", "tppcc021" };

				String[] Cakecups = { "White \n6 \n36X200",
						"White \n9.5 \n36X100", "White \n12.5 \n36X100",
						"Flora \n6 \n36X200", "Flora \n9.5 \n36X100",
						"Flora \n12.5 \n36X100", "Red(BULK) \n32 \n16X200",
						"Blue(BULK) \n32 \n16X200",
						"Orange(BULK) \n32 \n16X200",
						"Green(BULK) \n32 \n16X200",
						"Brown(BULK) \n32 \n16X200",
						"White(BULK) \n32 \n16X200", "Designed \n50 \n10X100",
						"Brown \n50 \n16X150", "White \n50 \n16X150",
						"Black \n50 \n16X150", "Brown[BULK] \n50 \n16X150",
						"White[BULK] \n50 \n16X150",
						"Black[BULK] \n50 \n16X150", "5.5 cm \n50X100",
						"6 cm \n50X100", "7.5 cm \n42X100", "9.5 cm \n25X100",
						"10.5 cm \n16X100", "11.5 cm \n16X100",
						"12.5 cm \n16X100", "14 cm \n16X100", "6 cm \n50X100",
						"7.5 cm \n42X100", "9.5 cm \n25X100",
						"10.5 cm \n16X100", "11.5 cm \n16X100",
						"12.5 cm \n16X100", "5.5 cm \n50X100", "6 cm \n50X100",
						"7.20 cm \n30X100", "8 cm \n30X100", "9 cm \n30X100",
						"10 cm \n20X100", "11 cm \n20X100", "12 cm \n20X100",
						"14 cm \n20X100", "5.5 cm \n50X100", "6 cm \n50X100",
						"9.5 cm \n25X100", "10.5 cm \n16X100",
						"11.5 cm \n16X100", "12.5 cm \n16X100" };

				// arrayListImages.add(Cakecupsimages);
				// arrayListDesc.add(Cakecups);

				arrayImages = Cakecupsimages;
				arrayDescs = Cakecups;

				Cakecupsimages = null;
				Cakecups = null;

			} else if (pos == 4) {

				String[] Pipingbagsimages = { "tppsc042", "tppsc041",
						"tplpp011", "tplpp012", "tplpp002", "tplpp003",
						"tplpp004", "tplpp005" };
				String[] PipingBags = { "Clear \n53X28 cm \n1X12 Rolls",
						"Green \n41X21 cm \n81X12 Rolls",
						"Dual Side \n30 cm \n24X125",
						"Dual Side \n40 cm \n24X125",
						"Clear \n40 cm \n1X1 Rolls",
						"Clear \n50 cm \n1X1 Rolls",

						"Clear \n55 cm \n1X1 Rolls",
						"Clear \n60 cm \n1X1 Rolls" };

				// arrayListImages.add(Pipingbagsimages);
				// arrayListDesc.add(PipingBags);

				arrayImages = Pipingbagsimages;
				arrayDescs = PipingBags;

				Pipingbagsimages = null;
				PipingBags = null;

			} else if (pos == 5) {

				String[] Glassineandsandwichpaperimages = { "mppbs008",
						"mppfs001", "one", "two" };
				String[] GlassinePaper = { "25mX30cm \n1X24",
						" \n12X800 sheets", "", "" };

				// arrayListImages.add(Glassineandsandwichpaperimages);
				// arrayListDesc.add(GlassinePaper);

				arrayImages = Glassineandsandwichpaperimages;
				arrayDescs = GlassinePaper;

				Glassineandsandwichpaperimages = null;
				GlassinePaper = null;

			} else if (pos == 6) {

				String[] Bakingpaperimages = { "mppfb001", "mppfb002",
						"mppbs002", "mppbs003", "mppbs006", "tplpb005",
						"tbdpp026", "tppfs004" };
				String[] BakingPaper = { "10mX30cm \n1X24", "75mX45cm \n1X6",
						"33X30 \n1X100", "60X40 \n1X500", "'75X45 \n 1X500",
						"1X10", "1X10", "33X27 \n12X100" };

				// arrayListImages.add(Bakingpaperimages);
				// arrayListDesc.add(BakingPaper);

				arrayImages = Bakingpaperimages;
				arrayDescs = BakingPaper;

				Bakingpaperimages = null;
				BakingPaper = null;
			}

		} else if (catPos == 7) {

			if (pos == 0) {

				String[] Freezerzipperbagsimages = { "tplzb001", "tplzb002",
						"tplzb003", "tplpb012", "tplpb013", "tplfz001",
						"tplpb006" };
				String[] FreezerZipperBag = { "Small \n19X10 \n24X50",
						"Big \n25X12 \n24X30", " \n18X16.5 \n24X24",
						" \n20.5X17 \n24X100", "21X18 \n24X50",
						"30X27 \n24X40", "40X30 \n24X30" };

				// arrayListImages.add(Freezerzipperbagsimages);
				// arrayListDesc.add(FreezerZipperBag);

				arrayImages = Freezerzipperbagsimages;
				arrayDescs = FreezerZipperBag;

				Freezerzipperbagsimages = null;
				FreezerZipperBag = null;

			} else if (pos == 1) {

				String[] RoastingBagsimages = { "mplrt003", "mplrt004",
						"mplrt005", "tbdpp018", "tbdpp019", "tbdpp020",
						"tbdpp021", "tplzb014", "tplzb015", "tplzb016",
						"tplzb017", "tbdpp013", "tbdpp014", "tbdpp015",
						"tbdpp016", "tbdpp017", "tbdpp023", "tbdpp024",
						"tbdpp025", "mplrt002", "tppsc073", "tppsc074",
						"tppsc075" };
				String[] RoastingBags = { "Medium 8 PCS \n38X25 \n4X48",
						"Large 5 PCS \n43X35 \n4X48",
						"Jumbo 3 PCS \n55X43 \n4X48", "LARGE  \n20X46 \n50X48",
						"MEDIUM  \n17X40 \n50X48", "SMALL  \n15X36 \n50X48",
						"EXTRA LARGE  \n23X52 \n50X24", "  \n20X15 \n25X20",
						"\n25X20 \n25X20", "\n32X22 \n25X20",
						"\n40X30 \n25X20", "BLUE \n51X66 \n20X24 RLS",
						"WHITE \n51X66 \n40X24 RLS",
						"ORANGE \n58X71 \n15X24 RLS",
						"WHITE \n58X71 \n30X24 RLS",
						"BLACK \n70X80 \n15X24 RLS", "\n18X23 \n24X300",
						"\n20X30 \n24X200", "\n25X35 \n24X120",
						"\n35X19 \n24X10", "30 GAL \n65X95 \n20X20 pkt",
						"50 GAL \n80X110 \n20X10 pkt",
						"78 GAL \n95X120 \n20X10 pkt" };

				// arrayListImages.add(RoastingBagsimages);
				// arrayListDesc.add(RoastingBags);

				arrayImages = RoastingBagsimages;
				arrayDescs = RoastingBags;

				RoastingBagsimages = null;
				RoastingBags = null;

			} else if (pos == 2) {

				String[] Tablecoversofraimages = { "tpltc004", "tbdpp012" };
				String[] TableCoversofra = { "100X130 \n1X20",
						"100X130 \n[50 SHEETS] \n1X20 RLS" };

				// arrayListImages.add(Tablecoversofraimages);
				// arrayListDesc.add(TableCoversofra);

				arrayImages = Tablecoversofraimages;
				arrayDescs = TableCoversofra;

				Tablecoversofraimages = null;
				TableCoversofra = null;

			} else if (pos == 3) {

				String[] Paperbagsimages = { "tpppb002", "tpppb004",
						"tpppb003", "Sachet and Sugar Tubes", "mppwp128",
						"mppwp121", "mppbp133", "tpppb050", "mppwp141",
						"mppwp202", "pppbp011", "pppwp024", "mppwp159",
						"mppbp129", "mppwn039", "mppwn041", "mppwn042",
						"mppwn043", "mpsbw039", "mpsbw030", "mpsbw031",
						"mpsbw033", "mpsbw032", "mpsbw034", "mppwn046",
						"mppwn045", "mppwn044", "mppbn005" };
				String[] PaperBags = { "23X13 \n1X500", "28X13 \n1X500",
						"18X34 \n1X500", "", "19.5X12X6 \n1X1000",
						"21X13X6 \n1X1000", "21X13X6 \n1X1000",
						"27X16X6 \n1X1000", "31X20X6 \n1X1000",
						"61X9X4 \n1X1000", "61X9X4 \n1X1000",
						"61X9X4 \n1X1000", "34X9X4 \n1X1000",
						"34X26X4 \n1X1000", "24.5X12X7 \n1X1000",
						"28.5X15X9 \n1X1000", "31X18X11 \n1X1000",
						"33X23X14 \n1X1000", "33X27X12 \n1X1000",
						"33X27X12 \n1X1000", "35X30X18 \n1X1000",
						"37X34X14 \n1X1000", "38X32X12 \n1X1000",
						"44X44X16 \n1X1000", "40X20X9 \n1X1000",
						"50X26X10 \n1X1000", "55X30X7 \n1X1000",
						"49X26X10.5 \n1X1000" };

				// arrayListImages.add(Paperbagsimages);
				// arrayListDesc.add(PaperBags);

				arrayImages = Paperbagsimages;
				arrayDescs = PaperBags;

				Paperbagsimages = null;
				PaperBags = null;

			}

		} else if (catPos == 8) {

			if (pos == 0) {
				String[] Glovesimages = { "thppw040", "thppw018", "thppw017",
						"thppw016", "thppw019", "thppw045", "thppw046",
						"thppw047", "thppw048", "thppw038", "thppw036",
						"thppw035", "thppw039", "thppw071", "thppw072",
						"thppw073", "thppw074", "thppw062", "thppw063",
						"thppw064", "thppw099", "thppw092", "thppw093",
						"thppw094", "thppw095", "thppw087", "thppw088",
						"thppw089", "thppw090", "thppw060", "thppw037",
						"thppw061", "thppw026", "thppw098", "thppw077",
						"thppw078", "thppw015", "thppw079", "thppw027",
						"thppw028", "thppw029" };
				String[] Gloves = { "Extra Small \n 10X100", "Small \n 10X100",
						"Medium \n 10X100", "Large \n 10X100",
						"Extra Large \n 10X95", "Small \n 10X100",
						"Medium \n 10X100", "Large \n 10X100",
						"Extra large \n 10X95", "Small \n 10X100",
						"Medium \n 10X100", "Large \n 10X100",
						"Extra large \n 10X100", "Small \n 10X100",
						"Medium \n 10X100", "Large \n 10X100",
						"Extra Large \n 10x100", "Small \n 10X100",
						"Medium \n 10X100", "Large \n 10X100",
						"X Large \n 10X100", "Small \n 10X100",
						"Medium \n 10X100", "Large \n 10X100",
						"Extra Large \n 10X100", "SMALL \n 10X100",
						"MEDIUM \n 10X100", "LARGE \n 10X100",
						"EXTRA LARGE \n 10X100", "Small \n 10X100",
						"Medium 10X100", "Large \n 10X100",
						"Clear \n PE GLoves \n 100X100",
						"Blue \n PE Gloves \n 100X100", "Clear \n 10X500",
						"Blue \n 10X500", "Embossed \n 50X100", "50X100",
						"Blue \n 10X100", "Green \n 10X100", "White \n 10X100" };

				// arrayListImages.add(Glovesimages);
				// arrayListDesc.add(Gloves);

				arrayImages = Glovesimages;
				arrayDescs = Gloves;

				Glovesimages = null;
				Gloves = null;

			} else if (pos == 1) {

				String[] Maskimages = { "thppw008", "thppw010", "thppw042",
						"thppw009", "thppw007", "thppw001", "thppw030",
						"thppw031", "thppw076", "thppw032" };
				String[] Mask = { "Non Woven-Blue \n 20X50",
						"NonWoven-Green \n 1X1000", "White \n 20X50",
						"NonWoven-Blue \n 1X1000", "20X50", "1X1000",
						"White \n 1X500", "White \n 10X100", "Blue \n 10X100",
						"10X100" };

				// arrayListImages.add(Maskimages);
				// arrayListDesc.add(Mask);

				arrayImages = Maskimages;
				arrayDescs = Mask;

				Maskimages = null;
				Mask = null;

			} else if (pos == 2) {

				String[] Headgearimgs = { "thppw004", "thppw020", "thppw002",
						"thppw021", "thppw005", "thppw003", "thppw011",
						"thppw012", "thppw013", "thppw014", "thppw022",
						"thppw024", "thppw070", "thppw033" };

				String[] HeadGear = { "9\" \n 10X10", "10\" \n 5X10",
						"11\" \n 10X10", "12\" 5X10", "9\" \n 10X20",
						"11.5\" \n 10X20", "Blue \n 1X1000", "Green \n 1X1000",
						"Red \n 1X1000", "White/Plain \n 1X1000",
						"Blue \n 1X1000", "White \n 1X1000", "Black \n 1X1000",
						"18\"S/wrap \n 1X1000" };

				// arrayListImages.add(Headgearimgs);
				// arrayListDesc.add(HeadGear);

				arrayImages = Headgearimgs;
				arrayDescs = HeadGear;

				Headgearimgs = null;
				HeadGear = null;

			} else if (pos == 3) {

				String[] Tissuepprimgs = { "tppnt065", "tppnt064", "tppnt013",
						"tppnt003", "tppnt014", "tppnt011", "tppnt167",
						"tppnt010", "tppnt066", "tppnt019", "tppnt015",
						"tppnt017", "tppnt018" };

				String[] TissuePaper = { "Small \n 50X100", "Large \n 20X250 ",
						"2 Ply \n 1X12", "C-Fold Paper White \n 20X150",
						"Inter Fold Paper White \n 20X150",
						"Facial Tissue Plain Box\n(80 Sheet) \n 1X48",
						"\nFalcon Facial Tissue \n(80 Sheet) \n 1X48",
						"\nFalcon Facial Tissue \n(140 Sheet) \n 1X30",
						"Toilet Paper Roll \n 10X10",
						"Mini Tork Paper 2 Ply \n 1X12",
						"M Tork Paper 1Ply \n 1X6", "M Tork Paper 2Ply \n 1X6",
						"M Tork Paper 2Ply Ind. \n 1X6", };

				// arrayListImages.add(Tissuepprimgs);
				// arrayListDesc.add(TissuePaper);

				arrayImages = Tissuepprimgs;
				arrayDescs = TissuePaper;

				Tissuepprimgs = null;
				TissuePaper = null;

			} else if (pos == 4) {

				String[] Dispenserimages = { "tdidp027", "tdidp028",
						"tdidp050", "tdidp068", "tdidp069", "tdidp070",
						"tdidp071", "tdidp006", "tdidp023", "tdidp024",
						"tdidp045", "tdidp061", "tdidp062", "tdidp001",
						"tdidp002", "tdidp004", "tdidp003", "tdidp005",
						"tdidp049", "tdidp058", "tdidp012", "tdidp014",
						"tdidp040", "tdidp041", "tdidp053", "tdidp055",
						"tdidp054", "tdidp057", "tdidp072", "tdidp073",
						"tdidp074", "tdidp039", "tdidp011", "tdidp060",
						"tdidp059", "tdidp063", "tdidp064", "tdidp052" };
				String[] Dispenser = { "", "", "", "WHITE \n [PD-508W] \n 1X1",
						"BLACK \n [PD-508D] \n 1X1",
						"ORANGE \n [PD-508Y] \n 1X1",
						"BLUE \n [PD-508B] \n 1X1", "", "", "", "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", "", "", "", "", "", "", "", "" };

				// arrayListImages.add(Dispenserimages);
				// arrayListDesc.add(Dispenser);

				arrayImages = Dispenserimages;
				arrayDescs = Dispenser;

				Dispenserimages = null;
				Dispenser = null;

			}

		} else if (catPos == 9) {

			if (pos == 0) {

				String[] HandyWrapimages = { "tdihw010", "tdihw002",
						"tdiim013", "tdihw001", "tdidp038", "tdidp022",
						"tdihw006" };
				String[] HandyWrapCutter = { "12\" ", "18\"", "", "", "", "",
						"" };

				// arrayListImages.add(HandyWrapimages);
				// arrayListDesc.add(HandyWrapCutter);

				arrayImages = HandyWrapimages;
				arrayDescs = HandyWrapCutter;

				HandyWrapimages = null;
				HandyWrapCutter = null;

			} else if (pos == 1) {

				String[] Impulseimages = { "tdiim003", "tdiim004", "tdiim005" };
				String[] ImpulseSealer = { "", "", "" };

				// arrayListImages.add(Impulseimages);
				// arrayListDesc.add(ImpulseSealer);

				arrayImages = Impulseimages;
				arrayDescs = ImpulseSealer;

				Impulseimages = null;
				ImpulseSealer = null;

			} else if (pos == 2) {
				String[] HandyFuelimages = { "tmisc002" };
				String[] HandyFuel = { "1X72" };

				// arrayListImages.add(HandyFuelimages);
				// arrayListDesc.add(HandyFuel);

				arrayImages = HandyFuelimages;
				arrayDescs = HandyFuel;

				HandyFuelimages = null;
				HandyFuel = null;

			} else if (pos == 3) {

				String[] Bagsealerimages = { "tdiim017", "tplpb032", "tplpb033" };
				String[] BagSealer = { "9MM[ET-609STK] \n 1X1",
						"RED \n 50X9MM \n 10X27", "GREEN \n 50X9MM \n 10X27" };

				// arrayListImages.add(Bagsealerimages);
				// arrayListDesc.add(BagSealer);

				arrayImages = Bagsealerimages;
				arrayDescs = BagSealer;

				Bagsealerimages = null;
				BagSealer = null;
			}
		}

		else {
			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}
		}

		gridView = (GridView) findViewById(R.id.gridview);

		new getImagesFromAssetsAsyncTask().execute();

		StringBuilder sb = new StringBuilder(title);
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		CommonUtility
				.getHeaderTitle(sb.toString(), ProductDetailsActivity.this);
	}

	public class getImagesFromAssetsAsyncTask extends
			AsyncTask<String, String, String> {

		Drawable[] imagesDrawables;

		protected void onPreExecute() {
			super.onPreExecute();

		}

		protected String doInBackground(String... args) {

			String[] imagesTitleArray = arrayImages;
			imagesDrawables = new Drawable[imagesTitleArray.length];

			for (int i = 0; i < imagesTitleArray.length; i++) {
				imagesDrawables[i] = CommonUtility.getBitmapFromAsset(activity,
						path, imagesTitleArray[i] + ".png");
			}

			return "";
		}

		protected void onPostExecute(String jsonResult) {

			// System.out.println(" result " + jsonResult);

			mProductsAdapter = new ProductsAdapter(activity, imagesDrawables);
			gridView.setAdapter(mProductsAdapter);
			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			// Toast.makeText(
			// activity,
			// "Images Length " + imagesDrawables.length + " and "
			// + "Desc Length " + arrayListDesc.size(),
			// Toast.LENGTH_LONG).show();
		}
	}

	public class ProductsAdapter extends BaseAdapter {

		private Activity activity;

		// public String description[], featuredEvents_id[];

		public LayoutInflater inflater;
		String[] frames;
		// used to keep selected position in ListView
		private int selectedPos = 0; // init value for selected 0/ for
										// not-selected- -1

		String flag;
		Drawable[] titles;
		Context context;

		public ProductsAdapter(Activity activity, Drawable[] titles) {

			this.activity = activity;
			this.titles = titles;

			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			System.out.println("images length " + titles.length
					+ " desc address " + arrayDescs.length);

		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Drawable getItem(int position) {
			// TODO Auto-generated method stub
			return titles[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public void setSelectedPosition(int pos) {
			selectedPos = pos;
			// inform the view of this change
			notifyDataSetChanged();
		}

		public int getSelectedPosition() {
			return selectedPos;
		}

		public class ViewHolder {
			TextView txtName, txtDetails;
			ImageView frame_img, bg_img;

			public ImageView imgViewFlag, imgFavorite, imgCart;
			public EditText catalogueTitleTxt;
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			/*
			 * Holder holder = new Holder(); View rowView; rowView =
			 * inflater.inflate(R.layout.gallery_list, null);
			 */

			ViewHolder holder = new ViewHolder();

			// if (!flag.equalsIgnoreCase("filter")) {

			if (convertView == null) {

				convertView = inflater.inflate(R.layout.row_productdetails,
						null);

				// } else {
				// rowView = inflater.inflate(R.layout.row_cataloguefilter,
				// null);
				// }

				holder.imgViewFlag = (ImageView) convertView
						.findViewById(R.id.container_image);
				holder.imgFavorite = (ImageView) convertView
						.findViewById(R.id.fav_image);
				holder.catalogueTitleTxt = (EditText) convertView
						.findViewById(R.id.title_txt);
				holder.imgCart = (ImageView) convertView
						.findViewById(R.id.cart_image);

				convertView.setTag(holder);

			} else {

				holder = (ViewHolder) convertView.getTag();

			}

			holder.catalogueTitleTxt.setTypeface(Constants
					.getProximanova_regular(activity));
			holder.imgCart.setTag(position);

			if (position % 2 == 0) {
				holder.imgFavorite.setImageResource(R.drawable.favorite);
				holder.imgFavorite.setTag(position + "," + position % 2);
			} else {
				holder.imgFavorite.setImageResource(R.drawable.favorite_holo);
				holder.imgFavorite.setTag(position + "," + position % 2);
			}

			// holder.imgViewFlag.setImageBitmap(CommonUtility.getBitmapFromAsset(
			// activity, path, frames[position]));
			// holder.imgViewFlag.setImageDrawable(CommonUtility
			// .getBitmapFromAsset(activity, path, titles[position]
			// + ".png"));

			holder.imgViewFlag.setImageDrawable(titles[position]);

			// holder.catalogueTitleTxt.setText(titles[position].replace(".png",
			// "").toUpperCase(Locale.ENGLISH)
			// + arrayListDesc.get(pos)[position]);

			holder.catalogueTitleTxt.setText(Html.fromHtml("<b><big>"
					+ arrayImages[position].replace(".png", "")
							.replace("_", " ").toUpperCase(Locale.ENGLISH)
					+ "</big></b></font><br>"
					+ arrayDescs[position].replace("\n", "<br>")));
			// System.out.println("");
			// if (position >= 0) {
			// if (null != pDialog && pDialog.isShowing()) {
			// pDialog.dismiss();
			// }
			// }
			return convertView;
		}
	}
}
