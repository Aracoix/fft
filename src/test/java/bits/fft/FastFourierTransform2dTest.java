/*
 * Copyright (c) 2014, Massachusetts Institute of Technology
 * Released under the BSD 2-Clause License
 * http://opensource.org/licenses/BSD-2-Clause
 */
package bits.fft;


import org.junit.Test;
import java.util.Random;


public class FastFourierTransform2dTest {

    private static final int OFFSET_0 = 3;
    private static final int DIM_0 = 8;

    private static final double[] INPUT_0 = {
            0.0,
            0.0,
            0.0,
            0.46193557475331404,
            -0.5189271686570283,
            0.2748348507002165,
            0.10087401023526787,
            0.19509055559440358,
            -0.33356320104670045,
            -0.229621630518563,
            0.9696830803996179,
            0.7583650357449603,
            0.8824983589642288,
            -0.45009206792903034,
            -0.7422056982524465,
            -0.7067966847069636,
            -0.9535237550322211,
            0.09347951439693114,
            0.9289737213537002,
            -0.7910186274980566,
            0.2502927269311186,
            -0.17840760901787656,
            0.552624582549865,
            0.981445571429566,
            -0.025534305939714397,
            0.49248281064466104,
            0.4663041403899877,
            0.6345941428186488,
            0.6777807000940366,
            0.05339886920973225,
            0.7986700232229871,
            -0.7321203188262155,
            -0.8338752035501702,
            0.9571486802956806,
            0.4447142383776974,
            0.4300620277009488,
            -0.7135592293988064,
            -0.07408436315515421,
            -0.9910287956342296,
            -0.8570033702402118,
            -0.3031595404166709,
            -0.3224606929284928,
            0.7187131027092959,
            0.9430939777034255,
            0.7314917604280766,
            0.2251622094197363,
            -0.6420240309423655,
            -0.564859175580628,
            0.7089743340845813,
            -0.9806530053980513,
            0.38458601390586655,
            0.5426259323413591,
            0.4253748562913786,
            -0.5775292501402076,
            0.5661849795343588,
            0.8906664779192579,
            -0.9715272897926641,
            -0.2115928944453378,
            0.7075815506161456,
            0.5720849016291052,
            0.986943910011628,
            0.766208811962958,
            -0.6594169395045921,
            0.9241378364150772,
            0.44859006715773764,
            0.35470832249974893,
            0.6087908344492714,
            -0.1171464526484165,
            -0.07582401942801109,
            0.7056549331989215,
            0.003669700411470078,
            0.9838859608204338,
            0.9385398198808321,
            -0.2937878556417637,
            -0.9054682616077412,
            -0.8567527531643988,
            -0.9417849745567284,
            -0.0326596197897997,
            0.9439002419074904,
            0.978234301502811,
            0.5348842060309797,
            0.002794702024459861,
            -0.48894937820711304,
            -0.38168362550362467,
            0.696561000544685,
            -0.8958309236520334,
            -0.9796490909275415,
            -0.2922940605825761,
            -0.826524289668545,
            0.7006230305286114,
            -0.9926461952885992,
            -0.38421366473105456,
            0.06321711249759532,
            0.8376284036771464,
            -0.44557994786257726,
            0.7485244205663888,
            0.21976302702552708,
            0.8172784193934717,
            -0.9110187596864099,
            0.2934478020777791,
            -0.006392472754687795,
            0.01340319190570538,
            0.04137763978589892,
            -0.26727851097200794,
            -0.04472617648615729,
            0.4079394106852692,
            -0.35446440351355735,
            -0.9766903234469044,
            0.4020778763648092,
            0.4907057207831018,
            0.21457649712523552,
            -0.5298365482716678,
            -0.2219136328927429,
            0.8229367236859715,
            -0.6652218228311861,
            -0.9921729387655582,
            -0.4702083450950567,
            0.7856339143123703,
            -0.40486347017843705,
            -0.14219104134052496,
            0.37510327636772756,
            -0.18300713059794815,
            -0.2938950011135606,
            0.34351713499907377,
            0.4808343603387655,
            0.7888704490126863,
            0.1599155665373344,
            0.02151095989651952,
            -0.37606052613241703,
            0.0804588083428397,
            0.9462153023499189
    };

    private static final double[] OUTPUT_0_COMPLEX = {
            7.352789251100225,
            1.2867466924764377,
            -4.080238626358627,
            -1.7917965127471094,
            -4.197941123908677,
            4.310759335455635,
            -1.4587083463576869,
            0.6414144404980213,
            -3.1304602158316985,
            1.1248998318543326,
            -0.5321193394179291,
            -8.30674058193657,
            1.8958046646825557,
            4.759099950517607,
            -0.2807287242462011,
            -5.325844368649624,
            3.0283498850767367,
            2.476847631217435,
            -1.6971915270957065,
            -4.753539474132585,
            0.39964494279895035,
            4.1920780172721255,
            -8.113748883098035,
            -4.139014753859456,
            -6.535039492431495,
            -15.262488105924977,
            -4.108425530013497,
            -0.7706532829447144,
            11.549724654149847,
            -4.307289123185573,
            -4.003964786429035,
            3.969076803559772,
            -0.6609300827589002,
            2.479844338151186,
            2.6439582785480855,
            0.9237223886236547,
            3.2303509329221747,
            -4.1380433751596275,
            -0.15420527192878808,
            0.38964064010197985,
            5.757950318184586,
            0.23305920714518003,
            2.1424020317133783,
            -0.9080058643332889,
            -9.041792451664143,
            2.7080240970678076,
            0.9859983138288136,
            5.0986046535706055,
            5.211233605610696,
            -5.823987813558039,
            -0.6580250432879087,
            -3.3542015932062283,
            4.793446674426763,
            5.964908670691768,
            11.742523357501602,
            -3.4061459275038493,
            -0.573432938303436,
            1.4917411068160207,
            -6.873092305712063,
            -2.5356457520333184,
            8.560864856546791,
            8.894824202856036,
            1.625863085752111,
            -2.4593311350068845,
            -5.7101823744875535,
            -2.082579088618523,
            7.866339270126645,
            -6.437203989263586,
            4.290272869912631,
            -8.747218559431605,
            1.4829998049850823,
            -2.8457460503309377,
            2.1588590066865363,
            0.011301304622766128,
            2.3018297547989075,
            3.4679864691553197,
            4.8313770316116464,
            -2.586368419181829,
            4.302890326842151,
            1.4860433017631243,
            -3.9170458017344227,
            1.0835761137003932,
            0.22993768787229496,
            7.784564809297417,
            -7.28987715491623,
            8.759204037754376,
            4.08868001628421,
            -0.28484958057581267,
            7.484036030454159,
            0.4581370094266326,
            0.3692424487564965,
            -3.0248946814149997,
            9.811782486444367,
            -2.8018123201128904,
            -3.345460643996846,
            -2.6544210977121336,
            6.087996663496295,
            -0.312905212426712,
            3.9907812649505994,
            -0.06451164914114443,
            -8.930737223573761,
            1.513509641416904,
            -9.420979465161036,
            0.12888080185377657,
            1.718501736779662,
            -6.481972943961855,
            -2.5986085754833876,
            1.5725219627504845,
            -1.7094365281842316,
            -4.5427616587683275,
            -0.10078126445063196,
            3.403601198204654,
            -8.214649962020925,
            3.5629321227731707,
            5.220695386378486,
            -8.467713837493104,
            1.2409860810608477,
            1.6629882119468498,
            -6.426757394560484,
            -1.2924478252375602,
            1.2795360723509666,
            0.9785955539590189,
            0.9590123121262155,
            -10.186892697942216,
            8.27763712146382,
            10.768627633038694,
            -5.585860364599895,
            -0.7020736977939153
    };


    private static final int OFFSET_1 = 3;
    private static final int DIM_1 = 8;

    private static final double[] INPUT_1 = {
            0.0,
            0.0,
            0.0,
            0.46193557475331404,
            -0.5189271686570283,
            0.2748348507002165,
            0.10087401023526787,
            0.19509055559440358,
            -0.33356320104670045,
            -0.229621630518563,
            0.9696830803996179,
            0.7583650357449603,
            0.8824983589642288,
            -0.45009206792903034,
            -0.7422056982524465,
            -0.7067966847069636,
            -0.9535237550322211,
            0.09347951439693114,
            0.9289737213537002,
            -0.7910186274980566,
            0.2502927269311186,
            -0.17840760901787656,
            0.552624582549865,
            0.981445571429566,
            -0.025534305939714397,
            0.49248281064466104,
            0.4663041403899877,
            0.6345941428186488,
            0.6777807000940366,
            0.05339886920973225,
            0.7986700232229871,
            -0.7321203188262155,
            -0.8338752035501702,
            0.9571486802956806,
            0.4447142383776974,
            0.4300620277009488,
            -0.7135592293988064,
            -0.07408436315515421,
            -0.9910287956342296,
            -0.8570033702402118,
            -0.3031595404166709,
            -0.3224606929284928,
            0.7187131027092959,
            0.9430939777034255,
            0.7314917604280766,
            0.2251622094197363,
            -0.6420240309423655,
            -0.564859175580628,
            0.7089743340845813,
            -0.9806530053980513,
            0.38458601390586655,
            0.5426259323413591,
            0.4253748562913786,
            -0.5775292501402076,
            0.5661849795343588,
            0.8906664779192579,
            -0.9715272897926641,
            -0.2115928944453378,
            0.7075815506161456,
            0.5720849016291052,
            0.986943910011628,
            0.766208811962958,
            -0.6594169395045921,
            0.9241378364150772,
            0.44859006715773764,
            0.35470832249974893,
            0.6087908344492714
    };

    private static final double[] OUTPUT_1 = {
            8.54658824633418,
            0.0,
            11.362827987931539,
            1.0050629096551287,
            3.489321301601039,
            3.7547477932816173,
            -4.520463841552702,
            0.7770636026627198,
            -0.7960154207442958,
            0.0,
            -4.520463841552701,
            -0.7770636026627238,
            3.4893213016010405,
            -3.7547477932816173,
            11.362827987931542,
            -1.0050629096551247,
            3.744645133893737,
            1.742659938137882,
            0.07361102895779537,
            0.2988757338949599,
            3.5756723276321347,
            4.4290845404665315,
            -7.106273965232945,
            0.09880676172813052,
            1.8189167523068648,
            0.8445409005785748,
            0.33868730146030956,
            0.281585053386211,
            -1.2195139147388885,
            3.386931395665019,
            -2.3693466548376785,
            0.2780511885243775,
            -4.312188441716635,
            5.385888368103531,
            4.096763036114245,
            -1.4854416570608524,
            -2.870779048652482,
            -2.2213133529737936,
            6.045149936070876,
            4.345400639933673,
            1.7723495230628248,
            3.03903451660518,
            0.9430813788678098,
            -6.0107842093923685,
            -0.16392029822839582,
            -2.3280358962316314,
            3.61266629536944,
            -4.682988160988792,
            2.3210087317539605,
            2.495469792468402,
            -2.4154114131711055,
            -6.497943718958659,
            4.55542963138893,
            1.052094537478288,
            -2.161609030651764,
            -1.6551290122060036,
            -1.781667621345439,
            -0.46694143421905776,
            6.590334262242057,
            0.15278645003051206,
            -4.342751257795458,
            -2.6203298515124884,
            -1.111754559024671,
            -2.4377842081299574,
            -4.691070522512083,
            0.0,
            -7.248446235390801,
            1.7643769305607928,
            1.8710445401216291,
            6.808331812377175,
            -1.8602952206098387,
            0.8564003800920421,
            1.0501991494815057,
            0.0,
            -1.8602952206098373,
            -0.8564003800920399,
            1.8710445401216331,
            -6.808331812377175,
            -7.248446235390802,
            -1.7643769305607946,
            2.3210087317539614,
            -2.495469792468403,
            -1.1117545590246716,
            2.4377842081299574,
            -4.342751257795458,
            2.6203298515124844,
            6.590334262242059,
            -0.1527864500305065,
            -1.781667621345439,
            0.4669414342190569,
            -2.1616090306517632,
            1.6551290122060032,
            4.5554296313889315,
            -1.0520945374782849,
            -2.4154114131711086,
            6.497943718958657,
            -4.312188441716633,
            -5.385888368103531,
            3.6126662953694364,
            4.682988160988791,
            -0.16392029822839715,
            2.3280358962316328,
            0.9430813788678081,
            6.010784209392367,
            1.7723495230628261,
            -3.03903451660518,
            6.045149936070878,
            -4.345400639933673,
            -2.8707790486524822,
            2.2213133529737923,
            4.096763036114245,
            1.4854416570608544,
            3.744645133893738,
            -1.7426599381378816,
            -2.369346654837679,
            -0.2780511885243766,
            -1.2195139147388872,
            -3.3869313956650204,
            0.33868730146031045,
            -0.2815850533862101,
            1.8189167523068648,
            -0.8445409005785739,
            -7.106273965232943,
            -0.09880676172813474,
            3.5756723276321356,
            -4.42908454046653,
            0.07361102895779437,
            -0.2988757338949606
    };

    private static final double[] OUTPUT_1_INV = {
            0.13354044134897156,
            0.0,
            0.1775441873114303,
            -0.015704107963361386,
            0.05452064533751624,
            -0.05866793427002527,
            -0.07063224752426096,
            -0.012141618791604997,
            -0.012437740949129621,
            0.0,
            -0.07063224752426095,
            0.01214161879160506,
            0.05452064533751626,
            0.05866793427002527,
            0.17754418731143035,
            0.015704107963361323,
            0.058510080217089644,
            -0.027229061533404406,
            0.0011501723274655526,
            -0.004669933342108749,
            0.055869880119252105,
            -0.06920444594478956,
            -0.11103553070676477,
            -0.0015438556520020394,
            0.028420574254794762,
            -0.013195951571540231,
            0.005291989085317337,
            -0.004399766459159547,
            -0.019054904917795133,
            -0.052920803057265924,
            -0.037021041481838726,
            -0.004344549820693398,
            -0.06737794440182242,
            -0.08415450575161768,
            0.06401192243928508,
            0.02321002589157582,
            -0.04485592263519503,
            0.034708021140215525,
            0.09445546775110744,
            -0.06789688499896364,
            0.027692961297856637,
            -0.04748491432195594,
            0.014735646544809529,
            0.09391850327175576,
            -0.0025612546598186847,
            0.03637556087861924,
            0.0564479108651475,
            0.07317169001544988,
            0.03626576143365563,
            -0.03899171550731878,
            -0.037740803330798524,
            0.10153037060872905,
            0.07117858799045203,
            -0.01643897714809825,
            -0.033775141103933815,
            0.025861390815718806,
            -0.027838556583522484,
            0.0072959599096727775,
            0.10297397284753214,
            -0.002387288281726751,
            -0.06785548840305403,
            0.04094265392988263,
            -0.017371164984760483,
            0.038090378252030585,
            -0.0732979769142513,
            0.0,
            -0.11325697242798126,
            -0.027568389540012388,
            0.029235070939400455,
            -0.10638018456839336,
            -0.02906711282202873,
            -0.013381255938938158,
            0.016409361710648527,
            0.0,
            -0.029067112822028708,
            0.013381255938938123,
            0.029235070939400518,
            0.10638018456839336,
            -0.11325697242798129,
            0.027568389540012415,
            0.03626576143365565,
            0.038991715507318794,
            -0.017371164984760493,
            -0.038090378252030585,
            -0.06785548840305403,
            -0.04094265392988257,
            0.10297397284753217,
            0.002387288281726664,
            -0.027838556583522484,
            -0.007295959909672764,
            -0.0337751411039338,
            -0.0258613908157188,
            0.07117858799045206,
            0.0164389771480982,
            -0.03774080333079857,
            -0.10153037060872902,
            -0.06737794440182239,
            0.08415450575161768,
            0.056447910865147444,
            -0.07317169001544986,
            -0.0025612546598187055,
            -0.03637556087861926,
            0.014735646544809501,
            -0.09391850327175573,
            0.027692961297856658,
            0.04748491432195594,
            0.09445546775110747,
            0.06789688499896364,
            -0.044855922635195035,
            -0.034708021140215504,
            0.06401192243928508,
            -0.02321002589157585,
            0.05851008021708966,
            0.0272290615334044,
            -0.03702104148183873,
            0.004344549820693384,
            -0.01905490491779511,
            0.052920803057265944,
            0.005291989085317351,
            0.004399766459159533,
            0.028420574254794762,
            0.013195951571540217,
            -0.11103553070676474,
            0.0015438556520021053,
            0.05586988011925212,
            0.06920444594478953,
            0.001150172327465537,
            0.004669933342108759
    };


    @Test public void testSpeed() {
        int dim = 512;
        int len = dim * dim * 2;

        double[] x = new double[len];
        Random rand = new Random( 0 );

        for( int i = 0; i < len; i++ ) {
            x[i] = rand.nextDouble() * 2.0 - 1.0;
        }

        double[] out = new double[len];
        FastFourierTransform2d trans = new FastFourierTransform2d( dim );

        Timer.start();

        for( int i = 0; i < 16; i++ ) {
            trans.applyComplex( x, 0, false, out, 0 );
        }

        System.out.println( "FastFourierTransform2d seconds: " + Timer.seconds() );
    }


    @Test public void testComplex() {
        final int dim = DIM_0;
        final int len = dim * dim * 2;
        final int off = 7;

        double[] b = new double[len + off];
        double[] c = new double[len + off];

        FastFourierTransform2d trans = new FastFourierTransform2d( dim );
        trans.applyComplex( INPUT_0, OFFSET_0, false, b, off );
        trans.applyComplex( b, off, true, c, off );

        //        System.out.println(TestUtil.complexToMatlab(INPUT_0, OFFSET_0, DIM_0, DIM_0));
        //        System.out.println("===");
        //        System.out.println(TestUtil.formatComplex(b, off, dim, dim));
        //        System.out.println("===");
        //        System.out.println(b[7] + "\t" + b[9]);
        //        System.out.println(INPUT_0[3] + "\t" + INPUT_0[5]);
        //        System.out.println("===");

        TestUtil.assertNear( OUTPUT_0_COMPLEX, 0, b, off, len );
        TestUtil.assertNear( INPUT_0, OFFSET_0, c, off, len );
    }


    @Test public void testReal() {
        final int dim = DIM_1;
        final int len = dim * dim * 2;
        final int off = 7;

        double[] b = new double[len + off];
        double[] c = new double[len + off];

        FastFourierTransform2d trans = new FastFourierTransform2d( dim );
        trans.applyReal( INPUT_1, OFFSET_1, false, b, off );
        trans.applyReal( INPUT_1, OFFSET_1, true, c, off );

        //System.out.println(TestUtil.realToMatlab(INPUT_1, OFFSET_1, dim, dim));
        //System.out.println("===");
        //System.out.println(TestUtil.formatComplex(c, off, dim, dim));
        //System.out.println("===");
        //System.out.println(TestUtil.arrayToJava(c, off, len, "OUTPUT_1_INV"));

        TestUtil.assertNear( OUTPUT_1, 0, b, off, len );
        TestUtil.assertNear( OUTPUT_1_INV, 0, c, off, len );
    }


}