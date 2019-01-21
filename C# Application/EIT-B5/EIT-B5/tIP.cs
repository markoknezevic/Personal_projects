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
    public partial class tIP : Form
    {
        public tIP()
        {
            InitializeComponent();
        }

        private void label1_Click(object sender, EventArgs e)
        {

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
        private void button3_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void button1_Click(object sender, EventArgs e)
        {

            try {
                if (textBox1.Text != string.Empty)
                {
                    Konekcija();

                    string prvo = "SELECT Arheolog.Ime,Arheolog.Prezime,Antikvitet.LokalitetID ";
                    string drugo = "FROM ((Antikvitet INNER JOIN Arheolog ON Antikvitet.ArheologID=Arheolog.ArheologID)INNER JOIN TIP_ANTIKVITETA ON Antikvitet.Tipantikviteta=TIP_ANTIKVITETA.TipAntikvitetaID) WHERE(Arheolog.ArheologID={0})";
                    komanda.CommandText = string.Format(prvo + drugo, Convert.ToInt32(textBox1.Text));

                    dt = new DataTable();
                    da = new OleDbDataAdapter();
                    da.SelectCommand = komanda;
                    da.Fill(dt);
                    dataGridView1.DataSource = dt;

                    konekcija.Close();
                }
                else
                    MessageBox.Show("Morate popuniti");

            }
            catch (Exception a)
            {
                MessageBox.Show("Pogresan unos");
            }
        }
        
    }
}
