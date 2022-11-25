package org.hussard.business.tools.nss;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.hussard.business.model.NirInformations;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Mois de naissance :
 * 2 et 3
 * deux derniers chiffres de l'année de naissance
 * (ce qui donne l’année à un siècle près) 	de 00 à 99
 */
class NumeroSecuSocialParseurTest {
    /**
     * Arguments.of("104052601414554", "2005"),
     * Arguments.of("286129147102722", "1986"),
     * Arguments.of("302021455505061", "2002"),
     * Arguments.of("455021455505041", "1955"),
     * Arguments.of("712021455505058", "2012"),
     * Arguments.of("840021455505091", "1940")
     * return Stream.of(
     * Arguments.of("105042601414554", "1"),
     * Arguments.of("286129147102722", "2"),
     * Arguments.of("305042601414551", "3"),
     * Arguments.of("405042601414501", "4"),
     * Arguments.of("705042601414545", "7"),
     * Arguments.of("805042601414592", "8")
     * Arguments.of("286069147102723", "06"),
     * Arguments.of("302121455505035", "12"),
     * Arguments.of("455031455505057", "03"),
     * Arguments.of("712091455505073", "09"),
     * Arguments.of("840015145550272", "01")
     * );
     */
    private static Stream<Arguments> nirValideMetropole() {
        return Stream.of(
                Arguments.of("1 05 01 26 014 145 06", "1", "2005", "01", "26", "014", "145", "06"),
                Arguments.of("1 05 01 2B 014 145 28", "1", "2005", "01", "2B", "014", "145", "28"),
                Arguments.of("1 05 01 2A 014 145 01", "1", "2005", "01", "2A", "014", "145", "01"),
                Arguments.of("105012601414506", "1", "2005", "01", "26", "014", "145", "06"),
                Arguments.of("105202601414519", "1", "2005", "20", "26", "014", "145", "19"),//Mois 20
                Arguments.of("105422601414580", "1", "2005", "42", "26", "014", "145", "80"),//Mois 42
                Arguments.of("105502601414514", "1", "2005", "50", "26", "014", "145", "14"),//Mois 50
                Arguments.of("105992601414522", "1", "2005", "99", "26", "014", "145", "22") //Mois 99
        );
    }
    private static Stream<Arguments> nirValideOutreMer() {
        return Stream.of(
                Arguments.of("1 05 01 97 014 145 29", "1", "2005", "01", "970", "14", "145", "29"),
                Arguments.of("1 05 20 98 914 145 78", "1", "2005", "20", "989", "14", "145", "78"),
                Arguments.of("1 05 42 97 523 145 62", "1", "2005", "42", "975", "23", "145", "62"),
                Arguments.of("1 05 50 98 099 145 79", "1", "2005", "50", "980", "99", "145", "79"),
                Arguments.of("1 05 99 98 258 518 85", "1", "2005", "99", "982", "58", "518", "85")
        );
    }
    private static Stream<Arguments> nirValideHorsFrance() {
        return Stream.of(
                Arguments.of("1 05 01 99 014 145 72", "1", "2005", "01", "99", "014", "145", "72"),
                Arguments.of("1 05 20 99 914 145 51", "1", "2005", "20", "99", "914", "145", "51"),
                Arguments.of("1 05 42 99 523 145 08", "1", "2005", "42", "99", "523", "145", "08"),
                Arguments.of("1 05 50 99 099 145 52", "1", "2005", "50", "99", "099", "145", "52"),
                Arguments.of("1 05 99 99 258 518 58", "1", "2005", "99", "99", "258", "518", "58")
        );
    }
    private static Stream<Arguments> nirNonValide() {
        return Stream.of(
                Arguments.of("505042601414548", "Code Sexe non valide !!"),
                Arguments.of("686129147102795", "Numéro de sécurité social(nir) non valide !!"),
                Arguments.of("186159147102723", "Mois de naissance non conforme !!"),
                Arguments.of("905042601414542", "Code Sexe non valide !!"),
                Arguments.of("005042601414507", "Code Sexe non valide !!")
        );
    }
    @ParameterizedTest
    @MethodSource("nirValideHorsFrance")
    void parse_Quand_NirValideHorsFrance_Attend_NirInformations(String nir,
                                                               String sexe,
                                                               String annee,
                                                               String mois,
                                                               String dep,
                                                               String numComm,
                                                               String numOrdre,
                                                               String cle) {
        NumeroSecuSocial parseur = new NumeroSecuSocial();
        NirInformations parse = parseur.parse(nir);

        assertNirInfotmations(sexe, annee, mois, dep, numComm, numOrdre, cle, parse);
    }
    @ParameterizedTest
    @MethodSource("nirValideMetropole")
    void parse_Quand_NirValideMetropole_Attend_NirInformations(String nir,
                                                      String sexe,
                                                      String annee,
                                                      String mois,
                                                      String dep,
                                                      String numComm,
                                                      String numOrdre,
                                                      String cle) {
        NumeroSecuSocial parseur = new NumeroSecuSocial();
        NirInformations parse = parseur.parse(nir);

        assertNirInfotmations(sexe, annee, mois, dep, numComm, numOrdre, cle, parse);
    }
    @ParameterizedTest
    @MethodSource("nirValideOutreMer")
    void parse_Quand_NirValideOutreMer_Attend_NirInformations(String nir,
                                                               String sexe,
                                                               String annee,
                                                               String mois,
                                                               String dep,
                                                               String numComm,
                                                               String numOrdre,
                                                               String cle) {
        NumeroSecuSocial parseur = new NumeroSecuSocial();
        NirInformations parse = parseur.parse(nir);

        assertNirInfotmations(sexe, annee, mois, dep, numComm, numOrdre, cle, parse);
    }
    private static void assertNirInfotmations(String sexe, String annee, String mois, String dep, String numComm, String numOrdre, String cle, NirInformations parse) {
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(parse.getSexe()).isEqualTo(sexe);
        softly.assertThat(parse.getAnneeNaiss()).isEqualTo(annee);
        softly.assertThat(parse.getMoisNais()).isEqualTo(mois);
        softly.assertThat(parse.getDepartementNaiss()).isEqualTo(dep);
        softly.assertThat(parse.getNumCommuneNaiss()).isEqualTo(numComm);
        softly.assertThat(parse.getNumOrdreNaiss()).isEqualTo(numOrdre);
        softly.assertThat(parse.getCleCtrNir()).isEqualTo(cle);

        softly.assertAll();
    }

    @ParameterizedTest()
    @MethodSource("nirNonValide")
    void parse_Quand_NirNonValide_Attend_Exception(String nir, String msgException) {
        NumeroSecuSocial parseur = new NumeroSecuSocial();
        Assertions.assertThatThrownBy(() -> parseur.parse(nir))
                .isInstanceOf(NumeroSecuSocialException.class)
                .hasMessage(msgException);
    }

}
