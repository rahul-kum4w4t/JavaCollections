package in.zero.link.mwaytree;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static in.zero.utils.Sequence.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MwaySearchTreeTest {

    final static Integer[] testData = {10, 1, 20, 15, 25, 17, 7, 21, 5, 33, 73, 71, 35, 42, 51, 28, 80, 11, 90};

    final static Integer[] levelOrderData5 = {21, 7, 15, 35, 71, 1, 5, 10, 11, 17, 20, 25, 28, 33, 42, 51, 73, 80, 90};
    final static Integer[] inOrderData5 = {1, 5, 7, 10, 11, 15, 17, 20, 21, 25, 28, 33, 35, 42, 51, 71, 73, 80, 90};
    final static Integer[] preOrderData5 = {21, 7, 1, 5, 15, 10, 11, 17, 20, 35, 25, 28, 33, 71, 42, 51, 73, 80, 90};
    final static Integer[] postOrderData5 = {1, 5, 10, 11, 7, 17, 20, 15, 25, 28, 33, 42, 51, 35, 73, 80, 90, 71, 21};
    final static Integer[] reverseOrderData5 = {90, 80, 73, 71, 51, 42, 35, 33, 28, 25, 21, 20, 17, 15, 11, 10, 7, 5, 1};
    final static Integer[] data1000 = {
            715, 739, 260, 755, 644, 367, 346, 212, 113, 992, 784, 516, 426, 535, 858, 975, 452, 811, 599, 888, 232, 153, 289, 165, 651, 164, 819, 312, 202, 668, 445, 199, 428, 513, 602, 498, 141, 252, 952, 265, 934, 815, 737, 171, 989, 133, 961, 16, 785, 447,
            156, 95, 789, 399, 521, 845, 372, 443, 640, 402, 772, 995, 895, 103, 803, 598, 684, 442, 38, 140, 275, 17, 34, 484, 927, 466, 650, 373, 561, 65, 294, 18, 186, 968, 953, 779, 88, 10, 169, 906, 587, 781, 308, 430, 634, 999, 908, 714, 475, 351,
            957, 801, 136, 363, 810, 145, 572, 93, 408, 938, 662, 470, 463, 851, 990, 284, 384, 91, 269, 675, 184, 259, 552, 744, 323, 82, 912, 946, 221, 813, 978, 227, 245, 167, 306, 950, 993, 878, 52, 207, 700, 509, 215, 90, 473, 665, 716, 8, 76, 504,
            974, 763, 251, 291, 796, 432, 369, 792, 615, 286, 120, 401, 79, 86, 588, 359, 872, 271, 702, 324, 532, 567, 342, 960, 709, 547, 112, 997, 429, 407, 617, 627, 488, 150, 752, 947, 660, 523, 601, 315, 655, 783, 835, 469, 738, 614, 710, 680, 123, 476,
            130, 510, 20, 49, 45, 244, 471, 574, 528, 829, 189, 497, 725, 896, 472, 625, 850, 151, 146, 707, 604, 461, 248, 657, 623, 246, 607, 39, 491, 605, 111, 355, 894, 608, 43, 195, 551, 928, 972, 73, 319, 268, 693, 297, 256, 376, 676, 11, 914, 1,
            129, 544, 833, 216, 682, 988, 683, 584, 441, 362, 390, 826, 240, 67, 264, 867, 913, 74, 78, 104, 622, 863, 325, 855, 846, 218, 54, 214, 27, 827, 954, 659, 29, 565, 691, 211, 944, 317, 907, 726, 787, 923, 751, 225, 345, 834, 433, 253, 374, 303,
            200, 591, 653, 823, 720, 566, 22, 336, 468, 903, 527, 255, 304, 209, 158, 877, 705, 758, 157, 226, 144, 19, 224, 474, 418, 852, 190, 859, 611, 412, 795, 777, 270, 554, 411, 932, 937, 234, 807, 32, 866, 464, 613, 72, 42, 223, 371, 331, 916, 496,
            360, 973, 354, 550, 310, 389, 421, 718, 393, 243, 830, 170, 148, 273, 3, 757, 386, 652, 502, 206, 75, 719, 119, 254, 564, 699, 703, 182, 596, 272, 51, 446, 620, 616, 24, 381, 971, 377, 507, 514, 485, 397, 55, 285, 80, 941, 12, 569, 478, 724,
            917, 576, 869, 361, 756, 674, 585, 686, 948, 85, 698, 489, 546, 132, 191, 690, 46, 920, 900, 593, 902, 984, 392, 155, 560, 868, 918, 549, 194, 802, 62, 687, 439, 794, 276, 64, 637, 871, 798, 935, 33, 210, 832, 143, 722, 898, 945, 849, 282, 970,
            57, 505, 633, 107, 267, 213, 711, 609, 96, 797, 570, 723, 327, 116, 482, 818, 955, 127, 736, 985, 420, 501, 97, 791, 677, 631, 893, 290, 409, 299, 519, 865, 713, 666, 121, 790, 996, 805, 263, 431, 788, 340, 30, 465, 238, 44, 370, 654, 761, 458,
            126, 160, 577, 87, 628, 329, 986, 383, 311, 770, 776, 479, 326, 843, 508, 589, 910, 177, 357, 89, 328, 137, 885, 247, 743, 694, 600, 642, 688, 506, 586, 647, 128, 656, 99, 338, 193, 624, 731, 922, 646, 518, 94, 575, 178, 541, 524, 9, 765, 356,
            839, 862, 837, 187, 415, 135, 728, 235, 875, 678, 661, 766, 775, 799, 911, 520, 388, 68, 500, 983, 228, 131, 423, 854, 413, 708, 734, 658, 192, 283, 786, 353, 649, 92, 578, 531, 196, 385, 147, 102, 573, 241, 612, 808, 750, 884, 965, 880, 664, 185,
            559, 538, 979, 648, 697, 712, 71, 583, 844, 943, 568, 98, 764, 298, 427, 558, 417, 35, 704, 467, 771, 494, 222, 626, 977, 540, 307, 233, 889, 453, 81, 706, 759, 37, 364, 330, 378, 821, 870, 536, 295, 204, 425, 60, 525, 641, 368, 892, 966, 635,
            732, 967, 492, 41, 930, 154, 6, 110, 347, 717, 746, 118, 179, 14, 740, 511, 733, 455, 333, 881, 581, 348, 857, 296, 122, 981, 899, 980, 820, 198, 172, 817, 873, 745, 773, 529, 301, 434, 926, 332, 197, 448, 901, 762, 258, 239, 886, 861, 459, 727,
            314, 800, 963, 462, 689, 760, 663, 831, 864, 201, 250, 444, 701, 343, 964, 108, 106, 778, 262, 278, 753, 416, 555, 279, 692, 281, 904, 450, 337, 456, 924, 313, 159, 349, 969, 883, 261, 400, 220, 592, 438, 59, 288, 100, 595, 515, 387, 804, 380, 512,
            109, 951, 161, 26, 534, 410, 991, 696, 230, 824, 847, 56, 667, 70, 876, 629, 480, 174, 579, 915, 422, 860, 490, 879, 13, 105, 774, 695, 842, 670, 454, 526, 242, 419, 277, 949, 249, 5, 747, 919, 188, 460, 545, 208, 729, 816, 320, 822, 219, 982,
            742, 257, 809, 2, 563, 925, 962, 168, 793, 841, 229, 571, 548, 987, 334, 437, 435, 606, 582, 40, 503, 7, 237, 58, 685, 553, 309, 50, 321, 352, 976, 217, 23, 292, 66, 543, 318, 486, 391, 998, 4, 848, 825, 0, 48, 274, 53, 882, 909, 115,
            449, 495, 748, 730, 853, 780, 77, 636, 139, 621, 814, 394, 812, 440, 300, 166, 874, 619, 828, 610, 404, 643, 398, 483, 638, 47, 287, 639, 936, 522, 205, 302, 499, 203, 782, 180, 395, 645, 537, 741, 322, 382, 477, 63, 436, 493, 840, 673, 891, 341,
            162, 556, 236, 838, 557, 366, 562, 175, 25, 942, 735, 69, 956, 887, 61, 618, 533, 603, 358, 138, 101, 152, 339, 15, 630, 632, 375, 405, 580, 335, 21, 721, 539, 836, 754, 451, 134, 517, 173, 31, 344, 36, 487, 481, 181, 142, 669, 597, 149, 939,
            994, 806, 403, 266, 679, 83, 767, 768, 406, 396, 905, 931, 114, 183, 672, 958, 856, 414, 379, 897, 769, 305, 590, 940, 28, 457, 933, 117, 959, 176, 921, 671, 84, 542, 163, 316, 424, 681, 280, 293, 929, 749, 125, 124, 530, 350, 231, 594, 365, 890
    };

    final static MwaySearchTree<Integer> numTreeAsc = new MwaySearchTree<>(Integer.class, 5);
    final static MwaySearchTree<Integer> numTreeDesc = new MwaySearchTree<>(Integer.class, 4, true);

    @BeforeAll
    static void beforeAll() {
        numTreeAsc.addAll(testData);
        numTreeDesc.addAll(testData);
    }

    @Test
    void testAdd() {

        final MwaySearchTree<Integer> numTree5 = new MwaySearchTree<>(Integer.class, 5);
        Arrays.stream(data1000).forEach(val -> {
            numTree5.add(val);
            assertTrue(numTree5.search(val));
        });
        assertEquals(1_000, Arrays.stream(numTree5.inOrder()).filter(Objects::nonNull).count(), "elements count mismatch");

        final MwaySearchTree<Integer> numTree12Asc = new MwaySearchTree<>(Integer.class, 12);
        Arrays.stream(data1000).forEach(val -> {
            numTree12Asc.add(val);
            assertTrue(numTree12Asc.search(val));
        });
        assertEquals(1_000, Arrays.stream(numTree12Asc.inOrder()).filter(Objects::nonNull).count(), "elements count mismatch");

        final MwaySearchTree<Integer> numTree12Desc = new MwaySearchTree<>(Integer.class, 12, true);
        Arrays.stream(data1000).forEach(val -> {
            numTree12Desc.add(val);
            assertTrue(numTree12Desc.search(val));
        });
        assertEquals(1_000, Arrays.stream(numTree12Desc.inOrder()).filter(Objects::nonNull).count(), "elements count mismatch");

        final MwaySearchTree<Integer> numTree12RightBias = new MwaySearchTree<>(Integer.class, 12);
        final BTree<Integer> numTree12LeftBias = new BTree<>(Integer.class, true, 12);
        getRandIntSeqInRange(0, 10_000).forEach(val -> {
            numTree12RightBias.add(val);
            assertTrue(numTree12RightBias.search(val));
        });
        assertEquals(10_000, Arrays.stream(numTree12RightBias.inOrder()).filter(Objects::nonNull).count(), "elements count mismatch");

        getRandIntSeqInRange(0, 10_000).forEach(val -> {
            numTree12LeftBias.add(val);
            assertTrue(numTree12LeftBias.search(val));
        });
        assertEquals(10_000, Arrays.stream(numTree12LeftBias.inOrder()).filter(Objects::nonNull).count(), "elements count mismatch");

    }

    @Test
    void testSearch() {

        List<Integer> data = getRandIntSeqInRange(0, 1_000);

        final BTree<Integer> numTree17RightBias = new BTree<>(Integer.class, 17);
        data.forEach(numTree17RightBias::add);

        assertTrue(data.stream().allMatch(numTree17RightBias::search), "Search values error");

        final BTree<Integer> numTree16LeftBias = new BTree<>(Integer.class, true, 16);
        data.forEach(numTree16LeftBias::add);

        assertTrue(data.stream().allMatch(numTree16LeftBias::search), "Search values error");

        final BTree<Integer> numTree16Inverse = new BTree<>(Integer.class, 16, true);
        data.forEach(numTree16Inverse::add);

        assertTrue(data.stream().allMatch(numTree16Inverse::search), "Search values error");
    }
}
