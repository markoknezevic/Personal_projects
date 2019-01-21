using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Data.OleDb;
namespace EIT_B5
{
    public partial class Periodi : Form
    {
        public Periodi()
        {
            InitializeComponent();
        }
        OleDbConnection konekcija;
        OleDbCommand komanda;
        DataTable dt;
        OleDbDataAdapter da;
        void Konekcija()
        {
            konekcija = new OleDbConnection();
            konekcija.ConnectionString = @"Provider=Microsoft.ACE.OLEDB.12.0;Data Source=|DataDirectory|\Antikviteti i lokacije - osnovno.accdb";
            konekcija.Open();
            komanda = new OleDbCommand();
            komanda.Connection = konekcija;
        }
        private void button1_Click(object sender, EventArgs e)
        {
            if (textBox1.Text != string.Empty && textBox2.Text != string.Empty)
            {
                Konekcija();
                string prvo = "INSERT INTO PERIOD(PeriodID,Period)";
                string drugo = "VALUES('{0}','{1}')";
                int br = Convert.ToInt32(textBox1.Text);
                int tip = Convert.ToInt32(textBox2.Text);
                komanda.CommandText = string.Format(prvo + drugo, br, tip);

                komanda.ExecuteNonQuery();
                MessageBox.Show("Podatak dodat u bazu");
                konekcija.Close();
            }
            else
                MessageBox.Show("Morate popuniti");
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (textBox1.Text != string.Empty && textBox2.Text != string.Empty)
            {
                Konekcija();
                komanda.CommandText = string.Format("DELETE FROM PERIOD WHERE PeriodID=" + Convert.ToInt32(textBox1.Text));
                komanda.ExecuteNonQuery();
                MessageBox.Show("Podatak obrisan iz baze");
                konekcija.Close();
            }
            else
                MessageBox.Show("Morate popuniti");
        }

        private void button3_Click(object sender, EventArgs e)
        {
            if (textBox1.Text != string.Empty && textBox2.Text != string.Empty)
            {
            Konekcija();
            komanda.CommandText = string.Format("UPDATE Period SET Period='{0}'  WHERE PeriodID={1}", Convert.ToInt32(textBox2.Text), Convert.ToInt32(textBox1.Text));
            komanda.ExecuteNonQuery();
            MessageBox.Show("Podatak je azuriran");
            konekcija.Close();
            }
            else
                MessageBox.Show("Morate popuniti");
        }

        private void button4_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
